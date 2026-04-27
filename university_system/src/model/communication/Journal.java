package model.communication;

import java.util.ArrayList;
import java.util.List;
import model.research.ResearchPaper;
import model.users.User;
import patterns.Subject;

public class Journal implements Subject {
    private String name;
    private List<ResearchPaper> papers;
    private List<User> subscribers;

    public Journal(String name) {
        this.name = name;
        this.papers = new ArrayList<>();
        this.subscribers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<ResearchPaper> getPapers() {
        return papers;
    }

    public List<User> getSubscribers() {
        return subscribers;
    }

    @Override
    public void subscribe(User user) {
        if (user != null && !subscribers.contains(user)) {
            subscribers.add(user);
        }
    }

    @Override
    public void unsubscribe(User user) {
        subscribers.remove(user);
    }

    // При публикации статьи журнал сохраняет её и уведомляет подписчиков.
    public void publishPaper(ResearchPaper paper) {
        if (paper != null) {
            papers.add(paper);
            notifySubscribers();
        }
    }

    @Override
    public void notifySubscribers() {
        for (User subscriber : subscribers) {
            System.out.println("Notification for " + subscriber + ": new paper was published in journal " + name);
        }
    }

    @Override
    public String toString() {
        return "Journal{" +
                "name='" + name + '\'' +
                ", papers=" + papers +
                ", subscribers=" + subscribers +
                '}';
    }
}
