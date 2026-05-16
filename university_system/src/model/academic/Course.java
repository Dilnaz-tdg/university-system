package model.academic;

import java.io.Serializable;
import java.util.*;

import core.DataStorage;
import model.users.Student;
import model.users.Teacher;

public class Course implements Serializable{
	private String courseId;
	private String name;
	private int credits;
	private CourseType type;
	
	private List<Teacher> lectureTeachers = new ArrayList<>();;
	private List<Teacher> practiceTeachers = new ArrayList<>();
	
	public Course(String courseId, String name, int credits, CourseType type) {
		 this.courseId = courseId;
	     this.name = name;
	     this.credits = credits;
	     this.type = type;
	}
	
	public void addLectureTeacher(Teacher teacher) {
		lectureTeachers.add(teacher);
	}
	
	public void addPracticeTeacher(Teacher teacher) {
		practiceTeachers.add(teacher);
	}
	
	public int getCredits() {
		return credits;
	}
	
	public List<Enrollment> getEnrollments() {
	    List<Enrollment> result = new ArrayList<>();

	    for (Enrollment e : DataStorage.getInstance().getEnrollments()) {
	        if (e.getCourse().equals(this)) {
	            result.add(e);
	        }
	    }
	    return result;
	}
	
	public List<Student> getStudents(){
		List<Student> students = new ArrayList<>();
		
	    for (Enrollment e : getEnrollments()) {
	    	if (e.isApproved()) {
                students.add(e.getStudent());
            }
	    }
	    return students;
	}
	
	@Override 
	public String toString() {
		return courseId + " " + " | credits: " + credits + " | name: " + name + " | " + type;
	}
	
	@Override 
	public boolean equals(Object obj) {
		if (this == obj) return true;
        if (!(obj instanceof Course)) return false;

        Course other = (Course) obj;
        return Objects.equals(courseId, other.courseId);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(courseId);
	}

	public String getCourseId() {return courseId;}

	public String getName() {
		return name;
	}
}

