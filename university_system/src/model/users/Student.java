package model.users;

import java.util.*;

import exceptions.CourseFailLimitException;
import exceptions.MaxCreditsException;
import model.academic.Course;
import model.academic.Enrollment;
import model.academic.Transcript;
import model.research.Researcher;
import patterns.DataStorage;

public class Student extends User{
	private double gpa;
	private String major;
	private int yearOfStudy;
	private int totalCredit;
	
	private List<Enrollment> enrollments = new ArrayList<>();
	private Transcript transcript = new Transcript(this);
	private Map<Course, Integer> failCount = new HashMap<>();
	private Researcher researchSupervisor;
	
	public Student(String firstName, String lastName, String login, String password, String major,int yearOfStudy) {
		super(firstName,lastName, login, password );
		this.major = major;
		this.yearOfStudy = yearOfStudy;
		
	}
	
	public Enrollment registerCourse(Course course) {
		if (totalCredit + course.getCredits() > 21) {
	        throw new MaxCreditsException(
	            "Cannot register. Max 21 credits exceeded."
	        );
	    }

		for (Enrollment e : enrollments) {
	            if (e.getCourse().equals(course)) return e ;
	    }
		 
		Enrollment enrollment = new Enrollment(this, course);
		enrollments.add(enrollment);
		DataStorage.getInstance().addEnrollment(enrollment);
		
		totalCredit += course.getCredits();
		return enrollment;
	}
	
	public void viewMarks() {
		transcript.print();
	}
	
	public List<Course> viewCourses() {
	    List<Course> courses = new ArrayList<>();

	    for (Enrollment e : enrollments) {
	        if (e.isApproved()) {
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
		return enrollments;
	}
	
	public void setResearchSupervisor(Researcher supervisor) {
		this.researchSupervisor = supervisor;
	}
	
	public void addFail(Course course) {
	    int count = failCount.getOrDefault(course, 0) + 1;
	    if (count > 3) {
	        throw new CourseFailLimitException(
	            "You failed this course more than 3 times"
	        );
	    }
	    failCount.put(course, count);
	}
	
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
