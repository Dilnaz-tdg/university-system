package model.users;

import java.time.LocalDate;
import java.util.*;

import core.DataStorage;
import core.Employee;
import core.User;
import model.academic.Course;
import model.academic.Enrollment;
import model.academic.Lesson;
import model.academic.Report;
import model.academic.ScheduleManager;
import model.communication.News;

public class Manager extends Employee {
	private ManagerType managerType;
	private static ScheduleManager sm = new ScheduleManager();
	
	public Manager(String firstName, String lastName, String login, String password, double salary, String department, String position, LocalDate hiringDate, ManagerType managerType){
		super(firstName, lastName,login,  password,  salary,  department,  position,  hiringDate);
		this.managerType = managerType;
	}
	
	public void approveEnrollment(Enrollment enrollment) {
		enrollment.approve(this);
	}
	
	public void rejectEnrollment(Enrollment enrollment) {
		enrollment.reject();
	}
	
	public void addCourseForRegistration(Course course) {
		DataStorage.getInstance().addCourse(course);
	}
	
	public void addLesson(Lesson lesson) {
	    sm.addLesson(lesson);
	}
	
	public void assignLectureTeacher(Course course, Teacher teacher) {
	    course.addLectureTeacher(teacher);
	    teacher.manageCourse(course);
	}
	public void assignPracticeTeacher(Course course, Teacher teacher) {
	    course.addPracticeTeacher(teacher);
	    teacher.manageCourse(course);
	}
	
	public Report createReport() {
		DataStorage ds = DataStorage.getInstance();
	    Map<String, Double> data = new HashMap<>();

	    int studentsCount = 0;
	    for (User u : ds.getUsers()) {
	        if (u instanceof Student) {
	            studentsCount++;
	        }
	    }

	    int coursesCount = ds.getCourses().size();

	    int totalEnrollments = ds.getEnrollments().size();
	    int approved = 0;
	    int rejected = 0;

	    for (Enrollment e : ds.getEnrollments()) {
	        if (e.isApproved()) {
	            approved++;
	        } else if (!e.isApproved()) {
	            rejected++;
	        }
	    }

	    data.put("Total Students", (double) studentsCount);
	    data.put("Total Courses", (double) coursesCount);
	    data.put("Total Enrollments", (double) totalEnrollments);
	    data.put("Approved Enrollments", (double) approved);
	    data.put("Rejected Enrollments", (double) rejected);

	    Report report = new Report("System Report", data);
	    report.generate(); 

	    return report;
	}
	
	public void manageViews(News news) {
	    news.pin();
	}
	
	public void viewStudentsSorted() {
	    List<User> users = DataStorage.getInstance().getUsers();
	    List<Student> students = new ArrayList<>();

	    for (User u : users) {
	        if (u instanceof Student) {
	            students.add((Student) u);
	        }
	    }
	    students.sort(Comparator.comparing(Student::getFirstName).thenComparing(Student::getLastName));

	    for (Student s : students) {
	        System.out.println(s);
	    }
	}
	
	  public void viewRequests() {
		  	List<Enrollment> enrollments = DataStorage.getInstance().getEnrollments();

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

