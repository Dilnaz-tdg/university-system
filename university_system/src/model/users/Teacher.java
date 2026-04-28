package model.users;

import java.time.LocalDate;
import java.util.*;

import enums.TeacherPosition;
import model.academic.Course;
import model.academic.Enrollment;
import model.academic.Mark;
import model.academic.Report;
import patterns.DataStorage;

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
	
	public void putMarks(Student student, Course course, Mark mark) {
	    for (Enrollment e : course.getEnrollments()) {
	        if (e.getStudent().equals(student)) {
	            mark = new Mark(e);
	            
	            DataStorage.getInstance().addMark(mark);
	            return;
	        }
	    }
	}
	
	public Report generateReport(Course course) {
		return null;
	}
	
	public boolean equals(Object obj) {
		 if (!(obj instanceof Teacher)) return false;
		    Teacher t = (Teacher) obj;
		    return Objects.equals(getLogin(), t.getLogin());
	}
	
	public int hashCode() {
		return Objects.hash(getLogin());
	}
	
	@Override
	public String toString() {
		return "Teacher: " + getFirstName() + " " + getLastName();
	}

	public void recieveRating(int rating) {
		this.rating = (this.rating + rating) / 2.0;
	}

	@Override
	public String getRole() {
		return "Teacher " + positionTitle.toString(); 
	}

	
}
