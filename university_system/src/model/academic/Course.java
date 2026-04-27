package model.academic;

import java.util.*;

import enums.CourseType;
import model.users.Student;
import model.users.Teacher;

public class Course {
	private String courseId;
	private String name;
	private int credits;
	private CourseType type;
	
	private List<Teacher> lectureTeachers = new ArrayList<>();;
	private List<Teacher> practiceTeachers = new ArrayList<>();;
	private List<Enrollment> enrollments = new ArrayList<>();;
	
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
	
	public void addEnrollment(Enrollment e) {
	    enrollments.add(e);
	}
	
	public int getCredits() {
		return credits;
	}
	
	public List<Enrollment> getEnrollments() {
		return enrollments;
	}
	
	public List<Student> getStudents(){
		List<Student> students = new ArrayList<>();
		
	    for (Enrollment e : enrollments) {
	    	if (e.isApproved()) {
                students.add(e.getStudent());
            }
	    }
	    return students;
	}
	
	@Override 
	public String toString() {
		return "\nCourse: " + courseId + " " + " | credits: " + credits + " | name: " + name + " | " + type;
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
}
