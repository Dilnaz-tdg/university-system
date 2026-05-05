package notification;

public interface Observer {
    // Получает текстовое уведомление от наблюдаемого объекта.
    void update(String message);
}
