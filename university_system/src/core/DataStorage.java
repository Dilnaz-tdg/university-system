package core;

import model.academic.Course;
import model.academic.Enrollment;
import model.academic.Lesson;
import model.academic.Mark;
import model.communication.News;
import model.communication.Request;
import notification.Journal;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataStorage implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String SAVE_FILE = "data/storage.ser";

    private static DataStorage instance;

    private List<User> users;
    private List<Course> courses;
    private List<News> news;
    private List<Journal> journals;
    private List<Request> requests;
    private List<Enrollment> enrollments;
    private List<Mark> marks;
    private List<Lesson> lessons;

    private DataStorage() {
        users = new ArrayList<>();
        courses = new ArrayList<>();
        news = new ArrayList<>();
        journals = new ArrayList<>();
        requests = new ArrayList<>();
        enrollments = new ArrayList<>();
        marks = new ArrayList<>();
        lessons = new ArrayList<>();
    }

    public static DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    public void save() {
        File file = new File(SAVE_FILE);
        File folder = file.getParentFile();

        if (folder != null && !folder.exists()) {
            folder.mkdirs();
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(this);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error while saving data: " + e.getMessage());
        }
    }

    public void load() {
        File file = new File(SAVE_FILE);

        if (!file.exists()) {
            System.out.println("No saved data found. New storage will be used.");
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            DataStorage loadedStorage = (DataStorage) in.readObject();

            this.users = loadedStorage.users;
            this.courses = loadedStorage.courses;
            this.news = loadedStorage.news;
            this.journals = loadedStorage.journals;
            this.requests = loadedStorage.requests;
            this.enrollments = loadedStorage.enrollments;
            this.marks = loadedStorage.marks;
            this.lessons = loadedStorage.lessons;

            System.out.println("Data loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error while loading data: " + e.getMessage());
        }
    }

    public void clear() {
        users.clear();
        courses.clear();
        news.clear();
        journals.clear();
        requests.clear();
        enrollments.clear();
        marks.clear();
        lessons.clear();
    }

    public List<Request> getRequests() { return requests; }
    public void addRequest(Request request) { requests.add(request); }

    public List<User> getUsers() { return users; }
    public void addUser(User user) { users.add(user); }

    public List<Course> getCourses() { return courses; }
    public void addCourse(Course course) { courses.add(course); }

    public List<News> getNews() { return news; }
    public void addNews(News n) { news.add(n); }

    public List<Journal> getJournals() { return journals; }
    public void addJournal(Journal journal) { journals.add(journal); }

    public List<Enrollment> getEnrollments() { return enrollments; }
    public void addEnrollment(Enrollment e) { enrollments.add(e); }

    public List<Mark> getMarks() { return marks; }
    public void addMark(Mark mark) { marks.add(mark); }

    public List<Lesson> getLessons() { return lessons; }
    public void addLesson(Lesson lesson) { lessons.add(lesson); }

    @Override
    public String toString() {
        return "\nDataStorage | users: " + users.size()
                + " | courses: " + courses.size()
                + " | enrollments: " + enrollments.size()
                + " | marks: " + marks.size();
    }
}
