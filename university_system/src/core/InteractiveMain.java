package core;

import model.academic.*;
import model.communication.*;
import model.research.*;
import model.users.*;
import notification.Journal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class InteractiveMain {

    private static final Scanner scanner = new Scanner(System.in);
    private static final DataStorage storage = DataStorage.getInstance();

    private static Admin admin;
    private static Manager orManager;
    private static Manager deptManager;
    private static Teacher lectureTeacher;
    private static Teacher practiceTeacher;
    private static Student student1;
    private static Student student2;
    private static TechSupportSpecialist support;

    private static Course oop;
    private static Course networks;
    private static Course math;

    public static void main(String[] args) {
        storage.clear();
        initializeDemoData();

        while (true) {
            System.out.println("\n========== RESEARCH-ORIENTED UNIVERSITY SYSTEM ==========");
            System.out.println("1. Login");
            System.out.println("2. Show all users");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    loginMenu();
                    break;
                case 2:
                    showAllUsers();
                    break;
                case 3:
                    storage.save();
                    System.out.println("Data saved. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void initializeDemoData() {
        admin = (Admin) UserFactory.createAdmin(Map.of(
                "firstName", "Adam", "lastName", "AdamSurname",
                "login", "adam", "password", "admin123",
                "salary", "700000", "department", "IT Department",
                "position", "System Administrator",
                "hiringDate", "2019-09-01"
        ));

        orManager = (Manager) UserFactory.createManager(Map.of(
                "firstName", "Oliver", "lastName", "OliverSurname",
                "login", "oliver", "password", "pass123",
                "salary", "400000", "department", "Office of Registrar",
                "position", "OR Manager", "hiringDate", "2021-02-10",
                "managerType", "OR"
        ));

        deptManager = (Manager) UserFactory.createManager(Map.of(
                "firstName", "Mark", "lastName", "MarkSurname",
                "login", "mark", "password", "pass123",
                "salary", "380000", "department", "CS Department",
                "position", "Department Manager", "hiringDate", "2020-08-15",
                "managerType", "DEPARTMENT"
        ));

        lectureTeacher = (Teacher) UserFactory.createTeacher(Map.of(
                "firstName", "Tom", "lastName", "TomSurname",
                "login", "tom", "password", "teach123",
                "salary", "550000", "department", "Computer Science",
                "position", "Senior Lecturer", "hiringDate", "2018-09-01",
                "positionTitle", "SENIOR_LECTOR"
        ));

        practiceTeacher = (Teacher) UserFactory.createTeacher(Map.of(
                "firstName", "Sara", "lastName", "SaraSurname",
                "login", "sara", "password", "teach123",
                "salary", "430000", "department", "Computer Science",
                "position", "Tutor", "hiringDate", "2022-01-20",
                "positionTitle", "TUTOR"
        ));

        student1 = (Student) UserFactory.createStudent(Map.of(
                "firstName", "Jake", "lastName", "JakeSurname",
                "login", "jake", "password", "stud123",
                "major", "Computer Science", "yearOfStudy", "2"
        ));

        student2 = (Student) UserFactory.createStudent(Map.of(
                "firstName", "Lily", "lastName", "LilySurname",
                "login", "lily", "password", "stud123",
                "major", "Information Systems", "yearOfStudy", "3"
        ));

        support = new TechSupportSpecialist(
                "Ben", "BenSurname", "ben", "sup123",
                230000, "IT Support", "Tech Specialist",
                LocalDate.of(2023, 3, 5)
        );

        admin.addUser(admin);
        admin.addUser(orManager);
        admin.addUser(deptManager);
        admin.addUser(lectureTeacher);
        admin.addUser(practiceTeacher);
        admin.addUser(student1);
        admin.addUser(student2);
        admin.addUser(support);

        oop = new Course("CS101", "Object-Oriented Programming", 5, CourseType.MAJOR);
        networks = new Course("NET201", "Computer Networks", 4, CourseType.MAJOR);
        math = new Course("MATH101", "Discrete Mathematics", 3, CourseType.MINOR);

        orManager.addCourseForRegistration(oop);
        orManager.addCourseForRegistration(networks);
        orManager.addCourseForRegistration(math);

        orManager.assignLectureTeacher(oop, lectureTeacher);
        orManager.assignPracticeTeacher(oop, practiceTeacher);
        orManager.assignLectureTeacher(networks, lectureTeacher);

        orManager.addLesson(new Lesson(
                lectureTeacher, oop, "Room 301",
                LocalDateTime.of(2026, 9, 5, 9, 0),
                LessonType.LECTURE
        ));

        orManager.addLesson(new Lesson(
                practiceTeacher, oop, "Lab 102",
                LocalDateTime.of(2026, 9, 5, 11, 0),
                LessonType.PRACTICE
        ));
    }

    private static void loginMenu() {
        System.out.print("Login: ");
        String login = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        User currentUser = null;

        for (User user : storage.getUsers()) {
            if (user.login(login, password)) {
                currentUser = user;
                break;
            }
        }

        if (currentUser == null) {
            System.out.println("Incorrect login or password.");
            return;
        }

        System.out.println("Welcome, " + currentUser.getFullName());

        if (currentUser instanceof Admin) {
            adminMenu((Admin) currentUser);
        } else if (currentUser instanceof Manager) {
            managerMenu((Manager) currentUser);
        } else if (currentUser instanceof Teacher) {
            teacherMenu((Teacher) currentUser);
        } else if (currentUser instanceof Student) {
            studentMenu((Student) currentUser);
        } else if (currentUser instanceof TechSupportSpecialist) {
            techSupportMenu((TechSupportSpecialist) currentUser);
        }
    }

    private static void studentMenu(Student student) {
        while (true) {
            System.out.println("\n---------------- STUDENT MENU ----------------");
            System.out.println("1. View approved courses");
            System.out.println("2. Register for OOP");
            System.out.println("3. Register for Networks");
            System.out.println("4. View teachers of OOP");
            System.out.println("5. View marks");
            System.out.println("6. View transcript");
            System.out.println("7. Rate teacher");
            System.out.println("8. Logout");
            System.out.print("Choose option: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    System.out.println(student.viewApprovedCourses());
                    break;

                case 2:
                    registerCourse(student, oop);
                    break;

                case 3:
                    registerCourse(student, networks);
                    break;

                case 4:
                    student.viewTeachersOfCourse(oop);
                    break;

                case 5:
                    student.viewMarks();
                    break;

                case 6:
                    student.getTranscript().generate();
                    student.getTranscript().print();
                    break;

                case 7:
                    List<Teacher> teachers = new ArrayList<>();

                    for (Course course : student.viewApprovedCourses()) {
                        for (Teacher teacher : course.getLectureTeachers()) {
                            if (!teachers.contains(teacher)) {
                                teachers.add(teacher);
                            }
                        }

                        for (Teacher teacher : course.getPracticeTeachers()) {
                            if (!teachers.contains(teacher)) {
                                teachers.add(teacher);
                            }
                        }
                    }

                    if (teachers.isEmpty()) {
                        System.out.println("No teachers available for rating. Register and get approved first.");
                        break;
                    }

                    System.out.println("Choose teacher to rate:");
                    for (int i = 0; i < teachers.size(); i++) {
                        System.out.println((i + 1) + ". " + teachers.get(i).getFullName());
                    }

                    System.out.print("Enter teacher number: ");
                    int teacherNumber = readInt();

                    if (teacherNumber < 1 || teacherNumber > teachers.size()) {
                        System.out.println("Invalid teacher number.");
                        break;
                    }

                    Teacher selectedTeacher = teachers.get(teacherNumber - 1);

                    System.out.print("Enter rating 1-5: ");
                    int rating = readInt();

                    if (rating < 1 || rating > 5) {
                        System.out.println("Rating must be from 1 to 5.");
                        break;
                    }

                    student.rateTeacher(selectedTeacher, rating);
                    System.out.println("You rated teacher " + selectedTeacher.getFullName() + " with " + rating + " stars.");
                    break;

                case 8:
                    student.logout();
                    return;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void teacherMenu(Teacher teacher) {
        while (true) {
            System.out.println("\n---------------- TEACHER MENU ----------------");
            System.out.println("1. View courses");
            System.out.println("2. View students of OOP");
            System.out.println("3. Put mark for student1");
            System.out.println("4. Generate OOP report");
            System.out.println("5. Send message to manager");
            System.out.println("6. Send complaint");
            System.out.println("7. Logout");
            System.out.print("Choose option: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    System.out.println(teacher.viewCourses());
                    break;
                case 2:
                    for (Student s : teacher.viewStudents(oop)) {
                        System.out.println(s);
                    }
                    break;
                case 3:
                    putDemoMark(teacher);
                    break;
                case 4:
                    Report report = teacher.generateReport(oop);
                    report.generate();
                    report.print();
                    break;
                case 5:
                    System.out.print("Enter message: ");
                    String msg = scanner.nextLine();
                    Message message = teacher.sendMessage(deptManager, msg);
                    System.out.println(message);
                    break;
                case 6:
                    System.out.print("Enter complaint: ");
                    String complaint = scanner.nextLine();
                    System.out.println(teacher.sendComplaint(complaint));
                    break;
                case 7:
                    teacher.logout();
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void managerMenu(Manager manager) {
        while (true) {
            System.out.println("\n---------------- MANAGER MENU ----------------");
            System.out.println("1. View enrollments");
            System.out.println("2. Approve all pending enrollments");
            System.out.println("3. Reject last enrollment");
            System.out.println("4. Add new course");
            System.out.println("5. Assign teacher to OOP");
            System.out.println("6. View students sorted");
            System.out.println("7. View teachers sorted");
            System.out.println("8. Create system report");
            System.out.println("9. Manage news");
            System.out.println("10. View requests");
            System.out.println("11. Logout");
            System.out.print("Choose option: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    manager.viewEnrollments();
                    break;
                case 2:
                    approveAllPending(manager);
                    break;
                case 3:
                    rejectLastEnrollment(manager);
                    break;
                case 4:
                    Course newCourse = new Course("AI101", "Artificial Intelligence", 4, CourseType.MAJOR);
                    manager.addCourseForRegistration(newCourse);
                    System.out.println("Course added: " + newCourse);
                    break;
                case 5:
                    manager.assignLectureTeacher(oop, lectureTeacher);
                    System.out.println("Teacher assigned.");
                    break;
                case 6:
                    manager.viewStudentsSorted();
                    break;
                case 7:
                    manager.viewTeachersSorted();
                    break;
                case 8:
                    Report report = manager.createReport();
                    report.generate();
                    report.print();
                    break;
                case 9:
                    News news = new News(
                            "Research Week 2026",
                            "Annual research conference starts soon.",
                            NewsTopic.RESEARCH,
                            manager
                    );
                    manager.manageNews(news);
                    storage.addNews(news);
                    System.out.println(news);
                    break;
                case 10:
                    manager.viewRequests();
                    break;
                case 11:
                    manager.logout();
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void adminMenu(Admin admin) {
        while (true) {
            System.out.println("\n---------------- ADMIN MENU ----------------");
            System.out.println("1. View all users");
            System.out.println("2. Add demo student");
            System.out.println("3. Remove student2");
            System.out.println("4. Update teacher salary");
            System.out.println("5. View logs");
            System.out.println("6. Logout");
            System.out.print("Choose option: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    showAllUsers();
                    break;
                case 2:
                    Student newStudent = (Student) UserFactory.createStudent(Map.of(
                            "firstName", "New", "lastName", "Student",
                            "login", "newstudent", "password", "12345",
                            "major", "Information Systems", "yearOfStudy", "1"
                    ));
                    admin.addUser(newStudent);
                    System.out.println("Student added: " + newStudent);
                    break;
                case 3:
                    admin.removeUser(student2);
                    System.out.println("Student removed.");
                    break;
                case 4:
                    lectureTeacher.setSalary(650000);
                    admin.updateUser(lectureTeacher);
                    System.out.println("Teacher updated.");
                    break;
                case 5:
                    admin.viewLogs();
                    break;
                case 6:
                    admin.logout();
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void techSupportMenu(TechSupportSpecialist support) {
        while (true) {
            System.out.println("\n------------- TECH SUPPORT MENU -------------");
            System.out.println("1. View new requests");
            System.out.println("2. Accept first request");
            System.out.println("3. Reject first request");
            System.out.println("4. Logout");
            System.out.print("Choose option: ");

            int choice = readInt();

            switch (choice) {
                case 1:
                    System.out.println(support.viewNewRequests());
                    break;
                case 2:
                    if (!support.getAssignedRequests().isEmpty()) {
                        support.acceptRequest(support.getAssignedRequests().get(0));
                        System.out.println("Request accepted.");
                    } else {
                        System.out.println("No assigned requests.");
                    }
                    break;
                case 3:
                    if (!support.getAssignedRequests().isEmpty()) {
                        support.rejectRequest(support.getAssignedRequests().get(0));
                        System.out.println("Request rejected.");
                    } else {
                        System.out.println("No assigned requests.");
                    }
                    break;
                case 4:
                    support.logout();
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void registerCourse(Student student, Course course) {
        try {
            Enrollment enrollment = student.registerCourse(course);
            System.out.println("Registered: " + enrollment);
        } catch (MaxCreditsException e) {
            System.out.println("Cannot register: " + e.getMessage());
        }
    }

    private static void putDemoMark(Teacher teacher) {
        List<Enrollment> enrollments = storage.getEnrollments();

        Enrollment target = null;

        for (Enrollment e : enrollments) {
            if (e.getStudent().equals(student1) && e.getCourse().equals(oop)) {
                target = e;
                break;
            }
        }

        if (target == null) {
            System.out.println("student1 is not registered for OOP.");
            return;
        }

        orManager.approveEnrollment(target);

        Mark mark = new Mark(target);
        mark.setAttestation1(27);
        mark.setAttestation2(28);
        mark.setFinalExam(36);

        teacher.putMark(student1, oop, mark);
        System.out.println("Mark added.");
    }

    private static void approveAllPending(Manager manager) {
        for (Enrollment e : storage.getEnrollments()) {
            if (!e.isApproved()) {
                manager.approveEnrollment(e);
            }
        }
        System.out.println("All pending enrollments approved.");
    }

    private static void rejectLastEnrollment(Manager manager) {
        List<Enrollment> enrollments = storage.getEnrollments();

        if (enrollments.isEmpty()) {
            System.out.println("No enrollments.");
            return;
        }

        Enrollment last = enrollments.get(enrollments.size() - 1);
        manager.rejectEnrollment(last);
        System.out.println("Rejected: " + last);
    }

    private static void showAllUsers() {
        for (User user : storage.getUsers()) {
            System.out.println(user);
        }
    }

    private static int readInt() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }
}