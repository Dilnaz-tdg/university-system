package model.communication;

import java.time.LocalDateTime;
import model.users.Employee;

public class Message {
    private Employee sender;
    private Employee receiver;
    private String text;
    private LocalDateTime sentAt;
    private boolean isRead;

    public Message(Employee sender, Employee receiver, String text) {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.sentAt = LocalDateTime.now();
        this.isRead = false;
    }

    public Employee getSender() {
        return sender;
    }

    public Employee getReceiver() {
        return receiver;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public boolean isRead() {
        return isRead;
    }

    // Помечаем сообщение как прочитанное после открытия получателем.
    public void markRead() {
        this.isRead = true;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender=" + sender +
                ", receiver=" + receiver +
                ", text='" + text + '\'' +
                ", sentAt=" + sentAt +
                ", isRead=" + isRead +
                '}';
    }
}
