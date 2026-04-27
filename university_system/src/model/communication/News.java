package model.communication;

import enums.NewsTopic;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.users.Manager;

public class News {
    private String title;
    private String content;
    private NewsTopic topic;
    private Manager author;
    private LocalDateTime date;
    private List<String> comments;
    private boolean isPinned;

    public News(String title, String content, NewsTopic topic, Manager author) {
        this.title = title;
        this.content = content;
        this.topic = topic;
        this.author = author;
        this.date = LocalDateTime.now();
        this.comments = new ArrayList<>();
        this.isPinned = false;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public NewsTopic getTopic() {
        return topic;
    }

    public Manager getAuthor() {
        return author;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public List<String> getComments() {
        return comments;
    }

    public boolean isPinned() {
        return isPinned;
    }

    // Добавляем текстовый комментарий к новости.
    public void addComment(String comment) {
        if (comment != null && !comment.isBlank()) {
            comments.add(comment);
        }
    }

    // Закрепляем новость, чтобы она отображалась выше остальных.
    public void pin() {
        this.isPinned = true;
    }

    public void unpin() {
        this.isPinned = false;
    }

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", topic=" + topic +
                ", author=" + author +
                ", date=" + date +
                ", comments=" + comments +
                ", isPinned=" + isPinned +
                '}';
    }
}

