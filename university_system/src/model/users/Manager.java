package model.users;

import java.time.LocalDate;
import java.util.*;

import enums.ManagerType;
import model.academic.Course;
import model.academic.Enrollment;
import model.academic.Report;

public class Manager extends Employee {
	private ManagerType managerType;
	
	public Manager(String firstName, String lastName, String login, String password, double salary, String department, String position, LocalDate hiringDate, ManagerType managerType){
		super(firstName, lastName,login,  password,  salary,  department,  position,  hiringDate);
		this.managerType = managerType;
	}
	
	public void approveEnrollment(Enrollment enrollment) {
		enrollment.approve();
	}
	
	public void rejectEnrollment(Enrollment enrollment) {
		enrollment.reject();
	}
	
	public void addCourseForRegistration(Course course) {}
	
	public void assignCourseToTeacher(Course course, Teacher teacher) {
		 teacher.manageCourse(course);
	}
	
	public Report createReport() {
		return null;
	}
	
	public void manageNews() {}
	
	public void viewStudentsSorted() {}
	
	  public void viewRequests(List<Enrollment> enrollments) {
	        for (Enrollment e : enrollments) {
	            System.out.println(e);
	        }
	    }
	
	@Override
	public String toString() {
		return "Manager: " + managerType;
		
	}

	@Override
	public String getRole() {
		return "Manager "  + managerType.toString();
	}
}
