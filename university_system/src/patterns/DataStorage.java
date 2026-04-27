package patterns;

import model.users.User;
import model.academic.Course;
import model.academic.Enrollment;
import model.academic.Mark;
import model.communication.News;
import model.communication.Journal;
import model.communication.Request;

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

    private DataStorage() {
        users = new ArrayList<>();
        courses = new ArrayList<>();
        news = new ArrayList<>();
        journals = new ArrayList<>();
        requests = new ArrayList<>();
        enrollments = new ArrayList<>();
        marks = new ArrayList<>();
    }

    public static DataStorage getInstance() { //единственный объект DataStorage
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    public void save() {
      
    }

    public void load() {
        
    }

    public List<Request> getRequests()          { return requests; }
    public void addRequest(Request request)      { requests.add(request); }

    public List<User> getUsers()                { return users; }
    public void addUser(User user)              { users.add(user); }

    public List<Course> getCourses()            { return courses; }
    public void addCourse(Course course)        { courses.add(course); }

    public List<News> getNews()                 { return news; }
    public void addNews(News n)                 { news.add(n); }

    public List<Journal> getJournals()          { return journals; }
    public void addJournal(Journal journal)     { journals.add(journal); }

    public List<Enrollment> getEnrollments()    { return enrollments; }
    public void addEnrollment(Enrollment e)     { enrollments.add(e); }

    public List<Mark> getMarks()                { return marks; }
    public void addMark(Mark mark)              { marks.add(mark); }

    @Override
    public String toString() {
        return "\nDataStorage | users: " + users.size() + " | courses: " + courses.size() + " | enrollments: " + enrollments.size() + " | marks: " + marks.size();
    }
}