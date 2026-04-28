package model.users;

import java.util.*;

import exceptions.CourseFailLimitException;
import exceptions.MaxCreditsException;
import model.academic.Course;
import model.academic.Enrollment;
import model.academic.Transcript;
import model.research.Researcher;

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
	
	public void registerCourse(Course course) {
		if (totalCredit + course.getCredits() > 21) {
	        throw new MaxCreditsException(
	            "Cannot register. Max 21 credits exceeded."
	        );
	    }

		for (Enrollment e : enrollments) {
	            if (e.getCourse().equals(course)) return;
	    }
		 
		Enrollment enrollment = new Enrollment(this, course);
		enrollments.add(enrollment);
		course.addEnrollment(enrollment);
		
		totalCredit += course.getCredits();
	}
	
	public void viewMarks() {
		transcript.print();
	}
	
	public Transcript getTranscript() {
		return transcript;
	}
	
	public void rateTeacher(Teacher teacher, int rating) {
		if (teacher != null) {
            teacher.addRating(rating);
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
		return "";
		/* return "\nStudent" + "\nID: " + id + "\nMajor: " + major + "\nGPA=" + gpa ;*/
	}
	
	@Override
	public boolean equals(Object o) {return false;}
	
	@Override
	public int hashCode() {return 0;}

	@Override
	public String getRole() {
		return "Student";
	}
}
