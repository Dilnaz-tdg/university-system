package model.users;

import java.time.LocalDate;
import java.util.*;

import enums.TeacherPosition;
import model.academic.Course;
import model.academic.Enrollment;
import model.academic.Mark;
import model.academic.Report;

public class Teacher extends Employee {
	private List<Course> courses =  new ArrayList<>();
	private TeacherPosition positionTitle;
	private double rating;
	
	public Teacher(String firstName, String lastName, String login, String password, double salary, String department, String position, LocalDate hiringDate,TeacherPosition positionTitle) {
		super(firstName, lastName,login,  password,  salary,  department,  position,  hiringDate);
		this.positionTitle = positionTitle;
		this.rating = 0;
	}
	
	public List<Course> viewCourses(){
		return courses;
	}
	
	public void manageCourse(Course course) {
		if (!courses.contains(course)) {
            courses.add(course);
        }
	}
	
	public List<Student> viewStudents(Course course){
		if(!courses.contains(course)) {
			System.out.println("You are not assigned to this course");
			return new ArrayList<>();
		}
		return course.getStudents() ;
	}
	
	public void putMarks(Student student, Course course, Mark mark) {}
	
	public Report generateReport(Course course) {
		return null;
	}
	
	public boolean equals(Object obj) {return false;}
	
	public int hashCode() {return 0;}
	
	public String toString() {
		return "";
	}

	public void addRating(int rating) {
		this.rating = (this.rating + rating) / 2.0;
	}

	@Override
	public String getRole() {
		return "Teacher " + positionTitle; 
	}

	
}
