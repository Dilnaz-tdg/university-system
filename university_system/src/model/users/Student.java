package model.users;

import java.util.*;

import core.DataStorage;
import core.User;
import model.academic.Course;
import model.academic.CourseFailLimitException;
import model.academic.Enrollment;
import model.academic.EnrollmentStatus;
import model.academic.MaxCreditsException;
import model.academic.Transcript;
import model.research.Researcher;
import model.research.LowHIndexException;

public class Student extends User {
    private static final long serialVersionUID = 1L;

    private String major;
    private int yearOfStudy;
    private int totalCredit;

    private Transcript transcript = new Transcript(this);
    private Map<Course, Integer> failCount = new HashMap<>();
    private Researcher researchSupervisor;

    public Student(String firstName, String lastName, String login, String password,
                   String major, int yearOfStudy) {
        super(firstName, lastName, login, password);
        this.major = major;
        this.yearOfStudy = yearOfStudy;
    }

    public Enrollment registerCourse(Course course) {
        if (totalCredit + course.getCredits() > 21) {
            throw new MaxCreditsException("Max 21 credits exceeded. Current: "
                    + totalCredit + ", course: " + course.getCredits());
        }

        for (Enrollment e : getEnrollments()) {
            if (e.getCourse().equals(course)) return null;
        }

        Enrollment e = new Enrollment(this, course);
        DataStorage.getInstance().addEnrollment(e);

        totalCredit += course.getCredits();
        return e;
    }

    public void viewTeachersOfCourse(Course course) {
        System.out.println("Teachers for course: " + course.getName());

        System.out.println("Lecture teachers:");
        for (Teacher teacher : course.getLectureTeachers()) {
            System.out.println(teacher);
        }

        System.out.println("Practice teachers:");
        for (Teacher teacher : course.getPracticeTeachers()) {
            System.out.println(teacher);
        }
    }

    public void viewMarks() {
        transcript.generate();
        transcript.print();
    }

    public List<Course> viewCourses() {
        List<Course> courses = new ArrayList<>();
        for (Enrollment e : getEnrollments()) {
            if (e.getStatus() == EnrollmentStatus.APPROVED) {
                courses.add(e.getCourse());
            }
        }
        return courses;
    }

    public Transcript getTranscript() {
        return transcript;
    }

    public void rateTeacher(Teacher teacher, int rating) {
        if (teacher != null) {
            teacher.recieveRating(rating);
        }
    }

    public List<Enrollment> getEnrollments() {
        List<Enrollment> result = new ArrayList<>();
        for (Enrollment e : DataStorage.getInstance().getEnrollments()) {
            if (e.getStudent().equals(this)) {
                result.add(e);
            }
        }
        return result;
    }

    public void setResearchSupervisor(Researcher supervisor) throws LowHIndexException {
        if (supervisor == null) {
            throw new LowHIndexException("Supervisor cannot be null.");
        }
        if (supervisor.calculateHIndex() < 3) {
            throw new LowHIndexException("Supervisor h-index must be at least 3.");
        }
        this.researchSupervisor = supervisor;
        System.out.println("Research supervisor assigned successfully.");
    }

    public void addFail(Course course) {
        int count = failCount.getOrDefault(course, 0) + 1;
        if (count > 3) {
            throw new CourseFailLimitException("You failed this course more than 3 times");
        }
        failCount.put(course, count);
    }

    public String getMajor() { return major; }
    public int getYearOfStudy() { return yearOfStudy; }
    public int getTotalCredit() { return totalCredit; }
    public Researcher getResearchSupervisor() { return researchSupervisor; }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName() + " " + major + " " + yearOfStudy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student s = (Student) o;
        return Objects.equals(getLogin(), s.getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin());
    }

    @Override
    public String getRole() {
        return "Student";
    }
}

