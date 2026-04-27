package patterns;

import model.users.User;

public interface Subject {
    void subscribe(User user);
    void unsubscribe(User user);
    void notifySubscribers();
}
