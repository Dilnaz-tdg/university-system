package model.academic;

import java.time.LocalDate;

import enums.EnrollmentStatus;
import model.users.Manager;
import model.users.Student;

public class Enrollment {
	private Student student;
	private Course course;
	private EnrollmentStatus status;
	private LocalDate registrationDate;
	private Manager approvedBy;
	
	public Enrollment(Student student, Course course) {
		this.student = student;
		this.course = course;
	    this.status = EnrollmentStatus.PENDING;
	    this.registrationDate = LocalDate.now();
	}
	
	public Student getStudent() {
		return student;
	}
	public Course getCourse() {
        return course;
    }
	
	public void approve(Manager manager) {
		status = EnrollmentStatus.APPROVED;
		approvedBy = manager;
	}
	
	public boolean isApproved() {
		return status == EnrollmentStatus.APPROVED;
	}
	
	public void reject() {
		status = EnrollmentStatus.REJECTED;
	}
	
	@Override
	public String toString() {
		 return "Enrollment:" +
			       "\nStudent: " + student +
			       "\nCourse: " + course +
			       "\nStatus: " + status +
			       "\nDate: " + registrationDate;
	}
}
