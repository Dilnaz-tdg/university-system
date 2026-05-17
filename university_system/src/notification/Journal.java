package notification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.research.ResearchPaper;

public class Journal implements Subject, Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private List<ResearchPaper> papers;
    private List<Observer> subscribers;

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

    public List<Observer> getSubscribers() {
        return subscribers;
    }

    @Override
    public void subscribe(Observer observer) {
        if (observer != null && !subscribers.contains(observer)) {
            subscribers.add(observer);
        }
    }

    @Override
    public void unsubscribe(Observer observer) {
        subscribers.remove(observer);
    }

    public void publishPaper(ResearchPaper paper) {
        if (paper != null) {
            papers.add(paper);
            notifySubscribers();
        }
    }

    @Override
    public void notifySubscribers() {
        for (Observer subscriber : subscribers) {
            subscriber.update("New paper was published in journal " + name);
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