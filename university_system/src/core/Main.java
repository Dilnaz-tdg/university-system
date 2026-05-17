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

    private static final String LINE = "══════════════════════════════════════";

    public static void main(String[] args) {

        System.out.println(LINE);
        System.out.println("  RESEARCH-ORIENTED UNIVERSITY SYSTEM DEMO");
        System.out.println(LINE);

        DataStorage storage = DataStorage.getInstance();
        storage.clear();

        System.out.println("\n" + LINE);
        System.out.println(" [1] USER CREATION & ADMIN MANAGEMENT");
        System.out.println(LINE);


        Admin admin = (Admin) UserFactory.createAdmin(Map.of(
                "firstName", "Adam", "lastName", "AdamSurname",
                "login", "adam", "password", "admin123",
                "salary", "700000", "department", "IT Department",
                "position", "System Administrator",
                "hiringDate", "2019-09-01"
        ));

        Manager orManager = (Manager) UserFactory.createManager(Map.of(
                "firstName", "Oliver", "lastName", "OliverSurname",
                "login", "oliver", "password", "pass123",
                "salary", "400000", "department", "Office of Registrar",
                "position", "OR Manager", "hiringDate", "2021-02-10",
                "managerType", "OR"
        ));

        Manager deptManager = (Manager) UserFactory.createManager(Map.of(
                "firstName", "Mark", "lastName", "MarkSurname",
                "login", "mark", "password", "pass123",
                "salary", "380000", "department", "CS Department",
                "position", "Department Manager", "hiringDate", "2020-08-15",
                "managerType", "DEPARTMENT"
        ));

        Teacher lectureTeacher = (Teacher) UserFactory.createTeacher(Map.of(
                "firstName", "Tom", "lastName", "TomSurname",
                "login", "tom", "password", "teach123",
                "salary", "550000", "department", "Computer Science",
                "position", "Senior Lecturer", "hiringDate", "2018-09-01",
                "positionTitle", "SENIOR_LECTOR"
        ));

        Teacher practiceTeacher = (Teacher) UserFactory.createTeacher(Map.of(
                "firstName", "Sara", "lastName", "SaraSurname",
                "login", "sara", "password", "teach123",
                "salary", "430000", "department", "Computer Science",
                "position", "Tutor", "hiringDate", "2022-01-20",
                "positionTitle", "TUTOR"
        ));

        Student student1 = (Student) UserFactory.createStudent(Map.of(
                "firstName", "Jake", "lastName", "JakeSurname",
                "login", "jake", "password", "stud123",
                "major", "Computer Science", "yearOfStudy", "2"
        ));

        Student student2 = (Student) UserFactory.createStudent(Map.of(
                "firstName", "Lily", "lastName", "LilySurname",
                "login", "lily", "password", "stud123",
                "major", "Information Systems", "yearOfStudy", "3"
        ));

        TechSupportSpecialist support = new TechSupportSpecialist("Ben", "BenSurname",
                "ben", "sup123", 230000, "IT Support", "Tech Specialist",
                LocalDate.of(2023, 3, 5));

        admin.addUser(admin);
        admin.addUser(orManager);
        admin.addUser(deptManager);
        admin.addUser(lectureTeacher);
        admin.addUser(practiceTeacher);
        admin.addUser(student1);
        admin.addUser(student2);
        admin.addUser(support);

        System.out.println("Users registered: " + storage.getUsers().size());

        lectureTeacher.setSalary(600000);
        admin.updateUser(lectureTeacher);
        System.out.println("Updated salary for " + lectureTeacher.getFullName() + ": " + lectureTeacher.getSalary());

        System.out.println("\n" + LINE);
        System.out.println(" [2] AUTHENTICATION");
        System.out.println(LINE);

        System.out.println("student1 correct login:  " + student1.login("jake", "stud123"));
        System.out.println("student2 correct login:  " + student2.login("lily", "stud123"));
        System.out.println("manager wrong password:  " + orManager.login("oliver", "wrongpass"));
        System.out.println("teacher correct login:   " + lectureTeacher.login("tom", "teach123"));

        System.out.println("\n" + LINE);
        System.out.println(" [3] COURSES & SCHEDULE");
        System.out.println(LINE);

        Course oop      = new Course("CS101",   "Object-Oriented Programming", 5, CourseType.MAJOR);
        Course networks = new Course("NET201",  "Computer Networks",           4, CourseType.MAJOR);
        Course math     = new Course("MATH101", "Discrete Mathematics",        3, CourseType.MINOR);

        orManager.addCourseForRegistration(oop);
        orManager.addCourseForRegistration(networks);
        orManager.addCourseForRegistration(math);

        orManager.assignLectureTeacher(oop, lectureTeacher);
        orManager.assignPracticeTeacher(oop, practiceTeacher);
        orManager.assignLectureTeacher(networks, lectureTeacher);

        System.out.println("OOP course: " + oop);
        System.out.println("Networks course: " + networks);

        Lesson oopLecture  = new Lesson(lectureTeacher,  oop,      "Room 301", LocalDateTime.of(2026, 9, 5, 9,  0), LessonType.LECTURE);
        Lesson oopPractice = new Lesson(practiceTeacher, oop,      "Lab 102",  LocalDateTime.of(2026, 9, 5, 11, 0), LessonType.PRACTICE);
        Lesson netLecture  = new Lesson(lectureTeacher,  networks, "Room 302", LocalDateTime.of(2026, 9, 6, 10, 0), LessonType.LECTURE);

        orManager.addLesson(oopLecture);
        orManager.addLesson(oopPractice);
        orManager.addLesson(netLecture);

        System.out.println("\nScheduled lessons:");
        for (Lesson lesson : storage.getLessons()) {
            System.out.println("  " + lesson);
        }

        System.out.println("\n" + LINE);
        System.out.println(" [4] STUDENT COURSE REGISTRATION");
        System.out.println(LINE);

        Enrollment enroll1 = student1.registerCourse(oop);
        Enrollment enroll2 = student1.registerCourse(networks);
        Enrollment enroll3 = student2.registerCourse(oop);
        Enrollment enroll4 = student2.registerCourse(math);

        System.out.println("student1 registered for OOP and Networks.");
        System.out.println("student2 registered for OOP and Math.");


        System.out.println("\nTesting max credits (21 credit limit):");
        Course extra1 = new Course("EX101", "Extra Course 1", 7, CourseType.FREE_ELECTIVE);
        Course extra2 = new Course("EX102", "Extra Course 2", 6, CourseType.FREE_ELECTIVE);
        orManager.addCourseForRegistration(extra1);
        orManager.addCourseForRegistration(extra2);
        try {
            student1.registerCourse(extra1);
            student1.registerCourse(extra2);
            System.out.println("ERROR: exception was not thrown!");
        } catch (MaxCreditsException e) {
            System.out.println("MaxCreditsException caught: " + e.getMessage());
        }

        orManager.approveEnrollment(enroll1);
        orManager.approveEnrollment(enroll2);
        orManager.approveEnrollment(enroll3);
        orManager.approveEnrollment(enroll4);

        System.out.println("\nAll enrollments after approval:");
        orManager.viewEnrollments();

        System.out.println("\nstudent1 views teacher of OOP:");
        student1.viewTeachersOfCourse(oop);

        System.out.println("student1 approved courses: " + student1.viewCourses());

        System.out.println("\n" + LINE);
        System.out.println(" [5] MARKS & TRANSCRIPT");
        System.out.println(LINE);

        System.out.println("Teacher viewStudents(OOP):");
        for (Student s : lectureTeacher.viewStudents(oop)) {
            System.out.println("  " + s);
        }

        Mark mark1 = new Mark(enroll1);
        mark1.setAttestation1(27);
        mark1.setAttestation2(28);
        mark1.setFinalExam(36);
        lectureTeacher.putMark(student1, oop, mark1);

        Mark mark2 = new Mark(enroll3);
        mark2.setAttestation1(15);
        mark2.setAttestation2(14);
        mark2.setFinalExam(20);
        lectureTeacher.putMark(student2, oop, mark2);

        Mark mark3 = new Mark(enroll2);
        mark3.setAttestation1(25);
        mark3.setAttestation2(26);
        mark3.setFinalExam(30);
        lectureTeacher.putMark(student1, networks, mark3);

        System.out.println("\nstudent1 marks:");
        student1.viewMarks();

        System.out.println("\nstudent2 marks:");
        student2.viewMarks();

        System.out.println("\nstudent1 transcript:");
        Transcript transcript = student1.getTranscript();
        transcript.generate();
        transcript.print();

        System.out.println("\nTesting fail limit (max 3 fails per course):");
        try {
            student2.addFail(oop);
            student2.addFail(oop);
            student2.addFail(oop);
            student2.addFail(oop);
        } catch (CourseFailLimitException e) {
            System.out.println("CourseFailLimitException caught: " + e.getMessage());
        }

        System.out.println("\nTeacher report for OOP:");
        Report teacherReport = lectureTeacher.generateReport(oop);
        teacherReport.generate();
        teacherReport.print();

        System.out.println("\nStudent rates teacher:");
        student1.rateTeacher(lectureTeacher, 5);
        student2.rateTeacher(lectureTeacher, 4);
        System.out.println(lectureTeacher);

        System.out.println("\n" + LINE);
        System.out.println(" [6] MESSAGES, REQUESTS, COMPLAINTS");
        System.out.println(LINE);

        Message msg1 = lectureTeacher.sendMessage(deptManager, "Please check OOP course roster.");
        System.out.println("Message sent: " + msg1);

        Message msg2 = practiceTeacher.sendMessage(orManager, "Lab equipment needs repair.");
        System.out.println("Message sent: " + msg2);

        Request request1   = orManager.sendRequest("Need system access for new semester.");
        Request complaint1 = lectureTeacher.sendComplaint("Projector in Room 301 is broken.");
        Request request2   = support.sendRequest("Server maintenance required.");

        System.out.println("\nManager views all requests:");
        orManager.viewRequests();

        support.getAssignedRequests().add(request1);
        support.getAssignedRequests().add(request2);
        System.out.println("\nTech support new requests: " + support.viewNewRequests());
        support.acceptRequest(request1);
        support.rejectRequest(request2);
        System.out.println("request1: " + request1);
        System.out.println("request2: " + request2);

        System.out.println("\n" + LINE);
        System.out.println(" [7] NEWS MANAGEMENT");
        System.out.println(LINE);

        News news1 = new News("Fall Semester Registration Open",
                "Students can now register for Fall 2026 courses.",
                NewsTopic.ANNOUNCEMENT, orManager);
        news1.addComment("Finally!");
        news1.addComment("When is the deadline?");

        News news2 = new News("Research Week 2026",
                "Annual research conference starts September 20th.",
                NewsTopic.RESEARCH, deptManager);

        deptManager.manageNews(news1);
        storage.addNews(news1);
        storage.addNews(news2);

        System.out.println(news1);
        System.out.println(news2);

        System.out.println("\n" + LINE);
        System.out.println(" [8] RESEARCH USERS & PAPERS");
        System.out.println(LINE);

        Professor professor = new Professor("Paul", "PaulSurname", "paul", "prof123",
                950000, "Computer Science", "Professor", LocalDate.of(2010, 9, 1));

        ResearchStudent researchStudent = new ResearchStudent("Rita", "RitaSurname",
                "rita", "res123", "Computer Science", 4);

        EmployeeResearcher empResearcher = new EmployeeResearcher("Eric", "EricSurname",
                "eric", "res123", 600000, "Research Office",
                "Research Coordinator", LocalDate.of(2019, 6, 15));

        ResearchTeacher researchTeacher = new ResearchTeacher("Nora", "NoraSurname",
                "nora", "res123", 620000, "Computer Science", "Lecturer",
                LocalDate.of(2017, 9, 1), TeacherPosition.LECTOR);

        admin.addUser(professor);
        admin.addUser(researchStudent);
        admin.addUser(empResearcher);
        admin.addUser(researchTeacher);

        ResearchPaper paper1 = new ResearchPaper(
                "Deep Learning for Medical Imaging",
                List.of("Paul PaulSurname", "Nora NoraSurname"),
                "IEEE Transactions on Medical Imaging",
                15, "10.1109/TMI.2025.001", 120,
                LocalDate.of(2025, 3, 10));

        ResearchPaper paper2 = new ResearchPaper(
                "Graph Neural Networks in NLP",
                List.of("Paul PaulSurname", "Rita RitaSurname"),
                "ACM Computing Surveys",
                22, "10.1145/ACM.2024.002", 75,
                LocalDate.of(2024, 8, 5));

        ResearchPaper paper3 = new ResearchPaper(
                "Federated Learning Security",
                List.of("Eric EricSurname", "Nora NoraSurname"),
                "Springer Journal of Cybersecurity",
                11, "10.1007/SECS.2024.003", 40,
                LocalDate.of(2024, 1, 20));

        ResearchPaper paper4 = new ResearchPaper(
                "Blockchain in Healthcare",
                List.of("Paul PaulSurname"),
                "IEEE Access",
                9, "10.1109/ACCESS.2023.004", 200,
                LocalDate.of(2023, 6, 14));

        ResearchPaper paper5 = new ResearchPaper(
                "Quantum Computing Overview",
                List.of("Eric EricSurname"),
                "Nature Reviews Physics",
                18, "10.1038/NRP.2022.005", 310,
                LocalDate.of(2022, 11, 30));

        professor.addPaper(paper1);
        professor.addPaper(paper2);
        professor.addPaper(paper4);

        researchStudent.addPaper(paper2);

        empResearcher.addPaper(paper3);
        empResearcher.addPaper(paper5);

        researchTeacher.addPaper(paper1);
        researchTeacher.addPaper(paper3);

        System.out.println("Professor h-index:       " + professor.calculateHIndex());
        System.out.println("ResearchStudent h-index: " + researchStudent.calculateHIndex());
        System.out.println("EmpResearcher h-index:   " + empResearcher.calculateHIndex());
        System.out.println("ResearchTeacher h-index: " + researchTeacher.calculateHIndex());

        System.out.println("\nProfessor papers sorted by citations:");
        professor.printPapers(new ResearchPaperCitationsComparator());

        System.out.println("\nEmpResearcher papers sorted by date:");
        empResearcher.printPapers(new ResearchPaperDateComparator());

        System.out.println("\nResearchTeacher papers sorted by pages:");
        researchTeacher.printPapers(new ResearchPaperPagesComparator());

        System.out.println("\n" + LINE);
        System.out.println(" [9] RESEARCH PROJECT");
        System.out.println(LINE);

        ResearchProject project = new ResearchProject("AI in Healthcare 2026");

        try {
            project.addParticipant(professor);
            project.addParticipant(researchStudent);
            project.addParticipant(empResearcher);
            project.addParticipant(researchTeacher);
            System.out.println("All researchers successfully joined the project.");
        } catch (NotResearcherException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\nOrdinary student tries to join research project:");
        try {
            project.addParticipant(student1);
        } catch (NotResearcherException e) {
            System.out.println("NotResearcherException caught: " + e.getMessage());
        }

        project.publishPaper(paper1);
        project.publishPaper(paper4);
        System.out.println("\nProject details: " + project);

        System.out.println("\n" + LINE);
        System.out.println(" [10] RESEARCH SUPERVISOR ASSIGNMENT");
        System.out.println(LINE);

        System.out.println("Professor h-index: " + professor.calculateHIndex());
        try {
            researchStudent.setResearchSupervisor(professor);
            System.out.println("Supervisor assigned successfully: " + professor.getFullName());
        } catch (LowHIndexException e) {
            System.out.println("LowHIndexException: " + e.getMessage());
        }

        System.out.println("\nTrying to assign supervisor with h-index < 3:");
        ResearchStudent lowHStudent = new ResearchStudent("Leo", "LeoSurname", "leo", "pass123", "CS", 4);
        try {
            researchStudent.setResearchSupervisor(lowHStudent);
        } catch (LowHIndexException e) {
            System.out.println("LowHIndexException caught: " + e.getMessage());
        }

        System.out.println("\n" + LINE);
        System.out.println(" [11] ALL UNIVERSITY RESEARCH PAPERS");
        System.out.println(LINE);

        List<Researcher> allResearchers = new ArrayList<>();
        allResearchers.add(professor);
        allResearchers.add(researchStudent);
        allResearchers.add(empResearcher);
        allResearchers.add(researchTeacher);

        printAllResearchPapers(allResearchers, new ResearchPaperDateComparator(),      "date published");
        printAllResearchPapers(allResearchers, new ResearchPaperCitationsComparator(), "citations");
        printAllResearchPapers(allResearchers, new ResearchPaperPagesComparator(),     "article length (pages)");

        System.out.println("\n" + LINE);
        System.out.println(" [12] TOP CITED RESEARCHER");
        System.out.println(LINE);

        Researcher topAll = findTopCitedResearcher(allResearchers);
        System.out.println("Top cited (university-wide): " + getResearcherName(topAll)
                + " — total citations: " + getTotalCitations(topAll));

        List<Researcher> csDeptResearchers = new ArrayList<>();
        csDeptResearchers.add(professor);
        csDeptResearchers.add(researchTeacher);
        csDeptResearchers.add(researchStudent);
        Researcher topCS = findTopCitedResearcher(csDeptResearchers);
        System.out.println("Top cited (CS department):   " + getResearcherName(topCS)
                + " — total citations: " + getTotalCitations(topCS));

        List<Researcher> byYear2024 = new ArrayList<>();
        for (Researcher r : allResearchers) {
            boolean hasIn2024 = r.getResearchPapers().stream()
                    .anyMatch(p -> p.getPublishedDate().getYear() == 2024);
            if (hasIn2024) byYear2024.add(r);
        }
        Researcher topYear = findTopCitedResearcherByYear(byYear2024, 2024);
        System.out.println("Top cited (year 2024):       " + getResearcherName(topYear)
                + " — citations in 2024: " + getTotalCitationsByYear(topYear, 2024));

        System.out.println("\n" + LINE);
        System.out.println(" [13] JOURNAL (OBSERVER PATTERN)");
        System.out.println(LINE);

        Journal journal = new Journal("KBTU Research Journal");
        journal.subscribe(orManager);
        journal.subscribe(student1);
        journal.subscribe(researchStudent);
        storage.addJournal(journal);

        System.out.println("Subscribers: " + journal.getSubscribers().size());
        journal.publishPaper(paper1);
        journal.publishPaper(paper4);

        journal.unsubscribe(student1);
        System.out.println("After unsubscribe — subscribers: " + journal.getSubscribers().size());
        journal.publishPaper(paper5);

        System.out.println("\nJournal: " + journal);

        System.out.println("\n" + LINE);
        System.out.println(" [14] MANAGER REPORTS & VIEWS");
        System.out.println(LINE);

        Report report = orManager.createReport();
        report.generate();
        report.print();

        System.out.println("\nManager views students sorted:");
        orManager.viewStudentsSorted();

        System.out.println("\nManager views teachers sorted:");
        orManager.viewTeachersSorted();

        System.out.println("\nManager rejects an enrollment:");
        Enrollment enroll5 = student2.registerCourse(networks);
        orManager.rejectEnrollment(enroll5);
        System.out.println("Enrollment status: " + enroll5);

        System.out.println("\n" + LINE);
        System.out.println(" [15] ADMIN LOGS");
        System.out.println(LINE);

        admin.viewLogs();

        System.out.println("\n" + LINE);
        System.out.println(" [16] DESIGN PATTERNS USED");
        System.out.println(LINE);

        System.out.println("1. Singleton  — DataStorage (single instance, save/load)");
        System.out.println("2. Factory    — UserFactory (Admin, Manager, Teacher, Student created via UserFactory)");
        System.out.println("3. Observer   — Journal (subscribe/publishPaper/notifySubscribers)");
        System.out.println("4. Strategy   — ResearchPaper Comparators (by date, citations, pages)");

        System.out.println("\n" + LINE);
        System.out.println(" [17] DATA STORAGE");
        System.out.println(LINE);

        System.out.println(storage);
        storage.save();
        System.out.println("Data saved successfully.");

        System.out.println("\n" + LINE);
        System.out.println(" [18] LOGOUT");
        System.out.println(LINE);

        student1.logout();
        student2.logout();
        lectureTeacher.logout();
        practiceTeacher.logout();
        orManager.logout();
        deptManager.logout();
        professor.logout();
        admin.logout();
        System.out.println("All users logged out.");

        System.out.println("\n" + LINE);
        System.out.println("  DEMO FINISHED SUCCESSFULLY");
        System.out.println(LINE);
    }

    private static void printAllResearchPapers(List<Researcher> researchers,
                                               Comparator<ResearchPaper> comparator,
                                               String sortType) {
        System.out.println("\nAll papers sorted by " + sortType + ":");
        Set<ResearchPaper> allPapers = new LinkedHashSet<>();
        for (Researcher r : researchers) {
            allPapers.addAll(r.getResearchPapers());
        }
        List<ResearchPaper> sorted = new ArrayList<>(allPapers);
        sorted.sort(comparator);
        for (ResearchPaper p : sorted) {
            System.out.println("  " + p);
        }
    }

    private static Researcher findTopCitedResearcher(List<Researcher> researchers) {
        if (researchers == null || researchers.isEmpty()) return null;
        Researcher top = researchers.get(0);
        for (Researcher r : researchers) {
            if (getTotalCitations(r) > getTotalCitations(top)) top = r;
        }
        return top;
    }

    private static Researcher findTopCitedResearcherByYear(List<Researcher> researchers, int year) {
        if (researchers == null || researchers.isEmpty()) return null;
        Researcher top = researchers.get(0);
        for (Researcher r : researchers) {
            if (getTotalCitationsByYear(r, year) > getTotalCitationsByYear(top, year)) top = r;
        }
        return top;
    }

    private static int getTotalCitations(Researcher researcher) {
        int total = 0;
        for (ResearchPaper p : researcher.getResearchPapers()) {
            total += p.getCitation();
        }
        return total;
    }

    private static int getTotalCitationsByYear(Researcher researcher, int year) {
        int total = 0;
        for (ResearchPaper p : researcher.getResearchPapers()) {
            if (p.getPublishedDate().getYear() == year) {
                total += p.getCitation();
            }
        }
        return total;
    }

    private static String getResearcherName(Researcher researcher) {
        if (researcher instanceof User) {
            return ((User) researcher).getFullName();
        }
        return researcher.toString();
    }
}

