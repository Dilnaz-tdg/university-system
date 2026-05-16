package model.users;

import java.time.LocalDate;
import java.util.*;

import core.DataStorage;
import core.Employee;
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
			throw new IllegalStateException("Teacher is not assigned to this course");
		}
		return course.getStudents() ;
	}
	
	public void putMarks(Student student, Course course, Mark mark) {
		if (!courses.contains(course)) {
			 throw new IllegalStateException("Teacher is not assigned to this course");
	    }
		
	    for (Enrollment e : course.getEnrollments()) {
	        if (e.getStudent().equals(student)) {

	            DataStorage.getInstance().addMark(mark);
	            return;
	        }
	    }
	}
	
	public Report generateReport(Course course) {
	    if (!courses.contains(course)) {
	    	throw new IllegalStateException("Teacher is not assigned to this course");
	    }

	    Map<String, Double> data = new HashMap<>();

	    int studentCount = course.getStudents().size();
	    int enrollmentCount = course.getEnrollments().size();

	    List<Mark> marks = DataStorage.getInstance().getMarks();

	    double total = 0;
	    int count = 0;
	    double max = Double.MIN_VALUE;
	    double min = Double.MAX_VALUE;
	    int passed = 0;

	    for (Mark m : marks) {
	        if (m.getEnrollment().getCourse().equals(course)) {

	            double value = m.getTotal();

	            total += value;
	            count++;

	            if (value > max) max = value;
	            if (value < min) min = value;

	            if (m.isPassed()) {
	                passed++;
	            }
	        }
	    }
	    double average = (count == 0) ? 0 : total / count;
	    double passRate = (count == 0) ? 0 : (passed * 100.0 / count);
	    if (count == 0) {
	        max = 0;
	        min = 0;
	    }
	    data.put("Students", (double) studentCount);
	    data.put("Enrollments", (double) enrollmentCount);
	    data.put("Average Total", average);
	    data.put("Max Total", max);
	    data.put("Min Total", min);
	    data.put("Pass Rate (%)", passRate);

	    return new Report("Course Report: " + course.getName(), data);
	}
	
	public void recieveRating(int rating) {
		this.rating = (this.rating + rating) / 2.0;
	}

	@Override
	public String getRole() {
		return "Teacher " + positionTitle.toString(); 
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

	
}

