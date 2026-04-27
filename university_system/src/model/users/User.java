package model.users;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID; //для автоматической генерации уникального id

public abstract class User implements Serializable {

    private static final long serialVersionUID = 1L;// для проверки версии класса когда сохранение

    private final String id;
    private String firstName;
    private String lastName;
    private String login;
    private String passwordHash;

    public User(String firstName, String lastName, String login, String password) {
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.passwordHash = hashPassword(password);// сразу хэшируем пароль
    }

    private String hashPassword(String password) {
        if (password == null) {
            return "";
        }
        return Integer.toHexString(password.hashCode());//шестнадцатеричную строку создает
    }

    public boolean login(String login, String password) {
        return Objects.equals(this.login, login)
                && Objects.equals(this.passwordHash, hashPassword(password));
    }

    public void logout() {
        System.out.println(getFullName() + " logged out.");
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (Objects.equals(this.passwordHash, hashPassword(oldPassword))) {
            this.passwordHash = hashPassword(newPassword);
            System.out.println("Password successfully changed.");
        } else {
            System.out.println("Incorrect old password.");
        }
    }

    public String getFullName() { return firstName + " " + lastName; }

    public String getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getLogin() { return login; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setLogin(String login) { this.login = login; }

    public void setPassword(String newPassword) { this.passwordHash = hashPassword(newPassword); }

    public abstract String getRole();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof User)) {
            return false;
        }

        User user = (User) obj;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return getRole() +" | id: " + id + " | name: " + getFullName() + " | login: " + login;
    }
}