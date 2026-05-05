package notification;

import core.User;

public interface Subject {
    void subscribe(User user);
    void unsubscribe(User user);
    void notifySubscribers();
}
