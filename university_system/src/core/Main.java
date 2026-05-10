package core;

import model.academic.*;
import model.communication.*;
import model.research.*;
import model.users.*;
import notification.Journal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("==============================================");
        System.out.println("RESEARCH-ORIENTED UNIVERSITY SYSTEM DEMO");
        System.out.println("==============================================");

        DataStorage storage = DataStorage.getInstance();

        System.out.println("\n[1] LOAD DATA");
        storage.load();

        System.out.println("\n[2] CREATE MAIN USERS");

        Admin admin = new Admin(
                "admin1Name",
                "admin1LastName",
                "admin1",
                "12345",
                700000,
                "department1",
                "adminPosition1",
                LocalDate.of(2021, 9, 1)
        );

        Manager manager = new Manager(
                "manager1Name",
                "manager1LastName",
                "manager1",
                "12345",
                350000,
                "department2",
                "managerPosition1",
                LocalDate.of(2022, 9, 1),
                ManagerType.DEPARTMENT
        );

        Teacher teacher = new Teacher(
                "teacher1Name",
                "teacher1LastName",
                "teacher1",
                "12345",
                500000,
                "department3",
                "teacherPosition1",
                LocalDate.of(2020, 8, 25),
                TeacherPosition.SENIOR_LECTOR
        );

        Teacher practiceTeacher = new Teacher(
                "teacher2Name",
                "teacher2LastName",
                "teacher2",
                "12345",
                420000,
                "department3",
                "teacherPosition2",
                LocalDate.of(2023, 1, 15),
                TeacherPosition.TUTOR
        );

        Student student = new Student(
                "student1Name",
                "student1LastName",
                "student1",
                "12345",
                "major1",
                2
        );

        TechSupportSpecialist support = new TechSupportSpecialist(
                "support1Name",
                "support1LastName",
                "support1",
                "12345",
                220000,
                "department4",
                "supportPosition1",
                LocalDate.of(2023, 2, 10)
        );

        admin.addUser(admin);
        admin.addUser(manager);
        admin.addUser(teacher);
        admin.addUser(practiceTeacher);
        admin.addUser(student);
        admin.addUser(support);

        System.out.println("\nAll users:");
        for (User user : storage.getUsers()) {
            System.out.println(user);
        }

        System.out.println("\n[3] AUTHENTICATION");

        if (student.login("student1", "12345")) {
            System.out.println("Student logged in successfully: " + student.getFullName());
        } else {
            System.out.println("Student login failed.");
        }

        if (manager.login("manager1", "wrong")) {
            System.out.println("Manager logged in successfully.");
        } else {
            System.out.println("Manager login failed because password is wrong.");
        }

        System.out.println("\n[4] ADMIN FUNCTIONS");

        admin.updateUser(student);
        admin.viewLogs();

        System.out.println("\n[5] CREATE COURSES");

        Course oop = new Course(
                "course1",
                "course1Name",
                5,
                CourseType.MAJOR
        );

        Course networks = new Course(
                "course2",
                "course2Name",
                4,
                CourseType.MAJOR
        );

        Course history = new Course(
                "course3",
                "course3Name",
                3,
                CourseType.FREE_ELECTIVE
        );

        manager.addCourseForRegistration(oop);
        manager.addCourseForRegistration(networks);
        manager.addCourseForRegistration(history);

        System.out.println("\nCourses in DataStorage:");
        for (Course course : storage.getCourses()) {
            System.out.println(course);
        }

        System.out.println("\n[6] ASSIGN MORE THAN ONE INSTRUCTOR PER COURSE");

        manager.assignLectureTeacher(oop, teacher);
        manager.assignPracticeTeacher(oop, practiceTeacher);

        System.out.println("Course after assigning teachers:");
        System.out.println(oop);

        System.out.println("\n[7] LESSONS: LECTURE AND PRACTICE");

        Lesson lecture = new Lesson(
                teacher,
                oop,
                "room1",
                LocalDateTime.of(2026, 5, 15, 10, 0),
                LessonType.LECTURE
        );

        Lesson practice = new Lesson(
                practiceTeacher,
                oop,
                "room2",
                LocalDateTime.of(2026, 5, 15, 12, 0),
                LessonType.PRACTICE
        );

        manager.addLesson(lecture);
        manager.addLesson(practice);

        storage.addLesson(lecture);
        storage.addLesson(practice);

        System.out.println("Lessons:");
        for (Lesson lesson : storage.getLessons()) {
            System.out.println(lesson);
        }

        System.out.println("\n[8] STUDENT COURSE REGISTRATION");

        Enrollment oopEnrollment = student.registerCourse(oop);
        Enrollment networkEnrollment = student.registerCourse(networks);

        if (oopEnrollment != null) {
            manager.approveEnrollment(oopEnrollment);
        }

        if (networkEnrollment != null) {
            manager.approveEnrollment(networkEnrollment);
        }

        System.out.println("Student courses:");
        for (Course course : student.viewCourses()) {
            System.out.println(course);
        }

        System.out.println("\n[9] TEACHER FUNCTIONS");

        System.out.println("Teacher courses:");
        for (Course course : teacher.viewCourses()) {
            System.out.println(course);
        }

        teacher.manageCourse(oop);

        System.out.println("Students of OOP course:");
        for (Student s : teacher.viewStudents(oop)) {
            System.out.println(s);
        }

        System.out.println("\n[10] MARKS AND TRANSCRIPT");

        if (oopEnrollment != null) {
            Mark mark = new Mark(oopEnrollment);
            mark.setAttestation1(28);
            mark.setAttestation2(30);
            mark.setFinalExam(35);

            teacher.putMarks(student, oop, mark);
            storage.addMark(mark);

            System.out.println("Total mark: " + mark.getTotal());
            System.out.println("Letter grade: " + mark.getLetterGrade());
            System.out.println("Passed: " + mark.isPassed());
        }

        student.viewMarks();
        student.getTranscript().generate();
        student.getTranscript().print();

        System.out.println("\n[11] STUDENT RATES TEACHER");

        student.rateTeacher(teacher, 5);
        System.out.println(teacher);

        System.out.println("\n[12] EMPLOYEE MESSAGE, REQUEST AND COMPLAINT");

        Message message = teacher.sendMessage(manager, "messageText1");
        System.out.println(message);

        Request request = manager.sendRequest("requestDescription1");
        Request complaint = teacher.sendComplaint("complaintDescription1");

        storage.addRequest(request);
        storage.addRequest(complaint);

        System.out.println("Requests in DataStorage:");
        for (Request r : storage.getRequests()) {
            System.out.println(r);
        }

        System.out.println("\n[13] TECH SUPPORT MODULE");

        support.getAssignedRequests().add(request);

        System.out.println("New requests:");
        System.out.println(support.viewNewRequests());

        support.acceptRequest(request);

        System.out.println("Updated request:");
        System.out.println(request);

        System.out.println("\n[14] NEWS MANAGEMENT");

        News news = new News(
                "newsTitle1",
                "newsContent1",
                NewsTopic.RESEARCH,
                manager
        );

        news.addComment("newsComment1");

        manager.manageNews(news);

        System.out.println("News object:");
        System.out.println(news);

        System.out.println("\n[15] JOURNAL AND OBSERVER PATTERN");

        Journal journal = new Journal("journal1Name");

        journal.subscribe(manager);
        journal.subscribe(support);

        storage.addJournal(journal);

        System.out.println("Subscribers added to journal.");

        System.out.println("\n[16] RESEARCH USERS");

        Professor professor = new Professor(
                "professor1Name",
                "professor1LastName",
                "professor1",
                "12345",
                900000,
                "department3",
                "professorPosition1",
                LocalDate.of(2018, 9, 1)
        );

        ResearchStudent researchStudent = new ResearchStudent(
                "researchStudent1Name",
                "researchStudent1LastName",
                "researchStudent1",
                "12345",
                "major1",
                4
        );

        EmployeeResearcher employeeResearcher = new EmployeeResearcher(
                "employeeResearcher1Name",
                "employeeResearcher1LastName",
                "employeeResearcher1",
                "12345",
                550000,
                "department5",
                "employeeResearcherPosition1",
                LocalDate.of(2022, 3, 5)
        );

        admin.addUser(professor);
        admin.addUser(researchStudent);
        admin.addUser(employeeResearcher);

        System.out.println(professor);
        System.out.println(researchStudent);
        System.out.println(employeeResearcher);

        System.out.println("\n[17] RESEARCH PAPERS");

        ResearchPaper paper = new ResearchPaper(
                "paper1Title",
                List.of("author1Name author1LastName", "author2Name author2LastName"),
                "journal1Name",
                12,
                "doi1",
                5,
                LocalDate.now()
        );

        ResearchPaper paper2 = new ResearchPaper(
                "paper2Title",
                List.of("author3Name author3LastName", "author4Name author4LastName"),
                "journal2Name",
                18,
                "doi2",
                50,
                LocalDate.of(2025, 4, 10)
        );

        ResearchPaper paper3 = new ResearchPaper(
                "paper3Title",
                List.of("author5Name author5LastName"),
                "journal3Name",
                9,
                "doi3",
                25,
                LocalDate.of(2024, 11, 20)
        );

        professor.addPaper(paper);
        professor.addPaper(paper2);
        professor.addPaper(paper3);

        researchStudent.addPaper(paper3);

        employeeResearcher.addPaper(paper);
        employeeResearcher.addPaper(paper2);

        System.out.println("Professor papers sorted by citations:");
        professor.printPapers(new ResearchPaperCitationsComparator());

        System.out.println("Employee researcher papers sorted by date:");
        employeeResearcher.printPapers(new ResearchPaperDateComparator());

        System.out.println("Research student papers sorted by pages:");
        researchStudent.printPapers(new ResearchPaperPagesComparator());

        System.out.println("\n[18] JOURNAL PUBLISHES PAPER");

        journal.publishPaper(paper);

        System.out.println("Journal object:");
        System.out.println(journal);

        System.out.println("\n[19] RESEARCH PROJECT");

        ResearchProject project = new ResearchProject("researchProject1Topic");

        try {
            project.addParticipant((Object) professor);
            project.addParticipant((Object) researchStudent);
            project.addParticipant((Object) employeeResearcher);

            System.out.println("Researchers joined project successfully.");
        } catch (NotResearcherException e) {
            System.out.println("Custom exception caught: " + e.getMessage());
        }

        try {
            project.addParticipant((Object) student);
        } catch (NotResearcherException e) {
            System.out.println("Custom exception caught because ordinary student is not Researcher: " + e.getMessage());
        }

        project.addPaper(paper);
        project.addPaper(paper2);
        project.publishPaper(paper3);

        System.out.println(project);

        System.out.println("\n[20] 4TH YEAR STUDENT SUPERVISOR CHECK");

        try {
            researchStudent.setResearchSupervisor(professor);
            System.out.println("Supervisor assigned successfully: " + professor.getFullName());
        } catch (LowHIndexException e) {
            System.out.println("Custom exception caught: " + e.getMessage());
        }

        try {
            researchStudent.setResearchSupervisor(researchStudent);
            System.out.println("Supervisor assigned successfully.");
        } catch (LowHIndexException e) {
            System.out.println("Custom exception caught because h-index is too low: " + e.getMessage());
        }

        System.out.println("\n[21] ALL RESEARCH PAPERS SORTED");

        List<Researcher> researchers = new ArrayList<>();
        researchers.add(professor);
        researchers.add(researchStudent);
        researchers.add(employeeResearcher);

        printAllResearchPapers(researchers, new ResearchPaperDateComparator(), "date");
        printAllResearchPapers(researchers, new ResearchPaperCitationsComparator(), "citations");
        printAllResearchPapers(researchers, new ResearchPaperPagesComparator(), "pages");

        System.out.println("\n[22] TOP CITED RESEARCHER");

        Researcher topResearcher = findTopCitedResearcher(researchers);

        if (topResearcher != null) {
            System.out.println("Top cited researcher: " + getResearcherName(topResearcher));
            System.out.println("Total citations: " + getTotalCitations(topResearcher));
        }

        System.out.println("\n[23] REPORT GENERATION");

        Report communicationReport = new Report(
                "report1Title",
                Map.of(
                        "OpenRequests", 1.0,
                        "PinnedNews", 1.0
                )
        );

        communicationReport.generate();
        communicationReport.print();

        Report academicReport = manager.createReport();
        academicReport.generate();
        academicReport.print();

        Report teacherReport = teacher.generateReport(oop);
        teacherReport.generate();
        teacherReport.print();

        System.out.println("\n[24] MANAGER VIEWS STUDENTS SORTED");

        manager.viewStudentsSorted();

        System.out.println("\n[25] COMPARABLE, COMPARATORS, EQUALS, HASHCODE, TOSTRING");

        System.out.println("paper.compareTo(paper2): " + paper.compareTo(paper2));
        System.out.println("paper.equals(paper2): " + paper.equals(paper2));
        System.out.println("paper.hashCode(): " + paper.hashCode());
        System.out.println("paper.toString(): " + paper);

        System.out.println("student.equals(researchStudent): " + student.equals(researchStudent));
        System.out.println("student.hashCode(): " + student.hashCode());
        System.out.println("student.toString(): " + student);

        System.out.println("\n[26] PATTERNS USED");

        System.out.println("1. Singleton Pattern: DataStorage.getInstance()");
        System.out.println("2. Factory Pattern: UserFactory.createUser()");
        System.out.println("3. Observer Pattern: Journal subscribe / publish / notifySubscribers");
        System.out.println("4. Strategy Pattern: Comparator objects for sorting research papers");

        System.out.println("\n[27] DATA STORAGE CHECK");

        System.out.println(storage);

        System.out.println("\n[28] SAVE DATA");

        storage.save();

        System.out.println("\n[29] LOGOUT");

        student.logout();
        teacher.logout();
        manager.logout();
        admin.logout();

        System.out.println("\n==============================================");
        System.out.println("DEMO FINISHED SUCCESSFULLY");
        System.out.println("==============================================");
    }

    private static void printAllResearchPapers(List<Researcher> researchers,
                                               Comparator<ResearchPaper> comparator,
                                               String sortType) {
        System.out.println("\nAll research papers sorted by " + sortType + ":");

        List<ResearchPaper> allPapers = new ArrayList<>();

        for (Researcher researcher : researchers) {
            allPapers.addAll(researcher.getResearchPapers());
        }

        allPapers.sort(comparator);

        for (ResearchPaper paper : allPapers) {
            System.out.println(paper);
        }
    }

    private static Researcher findTopCitedResearcher(List<Researcher> researchers) {
        if (researchers == null || researchers.isEmpty()) {
            return null;
        }

        Researcher top = researchers.get(0);

        for (Researcher researcher : researchers) {
            if (getTotalCitations(researcher) > getTotalCitations(top)) {
                top = researcher;
            }
        }

        return top;
    }

    private static int getTotalCitations(Researcher researcher) {
        int total = 0;

        for (ResearchPaper paper : researcher.getResearchPapers()) {
            total += paper.getCitation();
        }

        return total;
    }

    private static String getResearcherName(Researcher researcher) {
        if (researcher instanceof User) {
            User user = (User) researcher;
            return user.getFullName();
        }

        return researcher.toString();
    }
}