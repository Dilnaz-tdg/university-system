package model.users;

import enums.Language;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public abstract class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String firstName;
    private String lastName;
    private String login;
    private String passwordHash;
    private Language language;

    public User(String firstName, String lastName, String login, String password) {
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.passwordHash = hashPassword(password);
        this.language = Language.EN; 
    }

    private String hashPassword(String password) {
        return Integer.toHexString(password.hashCode());
    }

    public boolean login(String login, String password) {
        return this.login.equals(login) && this.passwordHash.equals(hashPassword(password));
    }

    public void logout() {
        System.out.println(firstName + " logged out.");
    }

    public void switchLanguage(Language lang) {
        this.language = lang;
        System.out.println("Language changed to: " + lang);
    }

    public String getId()            { return id; }
    public String getFirstName()     { return firstName; }
    public String getLastName()      { return lastName; }
    public String getLogin()         { return login; }
    public String getPasswordHash()  { return passwordHash; }
    public Language getLanguage()    { return language; }

    public void setFirstName(String firstName)   { this.firstName = firstName; }
    public void setLastName(String lastName)     { this.lastName = lastName; }
    public void setLogin(String login)           { this.login = login; }
    public void setLanguage(Language language)   { this.language = language; }

    public void setPassword(String newPassword) {
        this.passwordHash = hashPassword(newPassword);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "\nUser: " + id + " | name: " + firstName + " " + lastName + " | login: " + login + " | language: " + language;
    }
}