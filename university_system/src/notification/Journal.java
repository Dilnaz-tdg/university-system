package notification;

import java.util.ArrayList;
import java.util.List;

import core.User;
import model.research.ResearchPaper;


//  Класс Journal описывает научный журнал.
//  Журнал хранит статьи и список подписчиков, которых можно уведомлять о новых публикациях.

public class Journal implements Subject {
    private String name;
    private List<ResearchPaper> papers;
    private List<User> subscribers;

    //    Создаёт журнал по его названию.
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

    //    Подписывает пользователя на уведомления журнала.
    @Override
    public void subscribe(User user) {
        if (user != null && !subscribers.contains(user)) {
            subscribers.add(user);
        }
    }

    //    Убирает пользователя из списка подписчиков.
    @Override
    public void unsubscribe(User user) {
        subscribers.remove(user);
    }

    //    Публикует новую статью и запускает уведомление подписчиков.
    public void publishPaper(ResearchPaper paper) {
        if (paper != null) {
            papers.add(paper);
            notifySubscribers();
        }
    }

    //    Уведомляет всех подписчиков о новой публикации.
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
