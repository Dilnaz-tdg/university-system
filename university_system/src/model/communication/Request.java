package model.communication;

import enums.RequestStatus;
import enums.UrgencyLevel;
import java.time.LocalDate;
import model.users.Employee;

public class Request {
    private String id;
    private Employee author;
    private String description;
    private RequestStatus status;
    private LocalDate createdAt;
    private UrgencyLevel urgencyLevel;

    public Request(String id, Employee author, String description, UrgencyLevel urgencyLevel) {
        this.id = id;
        this.author = author;
        this.description = description;
        this.urgencyLevel = urgencyLevel;
        this.status = RequestStatus.NEW;
        this.createdAt = LocalDate.now();
    }

    public String getId() {
        return id;
    }

    public Employee getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public UrgencyLevel getUrgencyLevel() {
        return urgencyLevel;
    }

    // Метод нужен, чтобы техподдержка или система могли менять этап обработки заявки.
    public void updateStatus(RequestStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Request{" +
                "id='" + id + '\'' +
                ", author=" + author +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", urgencyLevel=" + urgencyLevel +
                '}';
    }
}
