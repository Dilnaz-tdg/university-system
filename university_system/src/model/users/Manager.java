package model.users;

import java.time.LocalDate;
import java.util.*;

import core.DataStorage;
import core.Employee;
import core.User;
import model.academic.Course;
import model.academic.Enrollment;
import model.academic.EnrollmentStatus;
import model.communication.Request;
import model.academic.Lesson;
import model.academic.Report;
import model.academic.ScheduleManager;
import model.communication.News;
import java.util.ArrayList;
import java.util.List;

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
	public void assignCourseToTeacher(Course course, Teacher teacher) {
	    assignLectureTeacher(course, teacher);
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
	
	public void manageNews(News news) {
		 if (news.isPinned()) {
		        news.unpin();
		    } else {
		        news.pin();
		    }
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
	    List<Request> requests = DataStorage.getInstance().getRequests();

	    if (requests.isEmpty()) {
	        System.out.println("No employee requests found.");
	        return;
	    }

	    for (Request request : requests) {
	        System.out.println(request);
	    }
	}
	
	public void viewEnrollments() {
	    List<Enrollment> enrollments = DataStorage.getInstance().getEnrollments();

	    if (enrollments.isEmpty()) {
	        System.out.println("No student registration requests found.");
	        return;
	    }

	    for (Enrollment enrollment : enrollments) {
	        System.out.println(enrollment);
	    }
	}
	
	public void viewTeachersSorted() {
	    List<Teacher> teachers = new ArrayList<>();

	    for (User u : DataStorage.getInstance().getUsers()) {
	        if (u instanceof Teacher) {
	            teachers.add((Teacher) u);
	        }
	    }

	    teachers.sort(Comparator.comparing(Teacher::getFirstName)
	            .thenComparing(Teacher::getLastName));

	    for (Teacher teacher : teachers) {
	        System.out.println(teacher);
	    }
	}
	
	public List<Student> searchStudentsByRegex(String regex) {
	    List<Student> result = new ArrayList<>();

	    for (User user : DataStorage.getInstance().getUsers()) {
	        if (user instanceof Student) {
	            Student student = (Student) user;

	            if (student.getFullName().matches(regex)) {
	                result.add(student);
	            }
	        }
	    }

	    return result;
	}

	public List<Course> searchCoursesByRegex(String regex) {
	    List<Course> result = new ArrayList<>();

	    for (Course course : DataStorage.getInstance().getCourses()) {
	        if (course.getName().matches(regex) || course.getCourseId().matches(regex)) {
	            result.add(course);
	        }
	    }

	    return result;
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

