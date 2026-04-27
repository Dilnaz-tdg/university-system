package model.users;

import patterns.DataStorage;

import java.io.*; //чтение логов, запись логов, создание папки logs
import java.time.LocalDate;
import java.time.LocalDateTime;//записывать дату и время действия в лог
import java.util.List;

public class Admin extends Employee {

    private static final long serialVersionUID = 1L;
    private static final String LOG_FILE = "logs/actions.log";//путь куда будут записываться действия администратора

    public Admin(String firstName, String lastName, String login, String password, double salary, String department, String position, LocalDate hiringDate) {
        super(firstName, lastName, login, password, salary, department, position, hiringDate);
    }

    public void addUser(User user) {
        DataStorage.getInstance().getUsers().add(user);
        log("ADD USER: " + user.getLogin());
        System.out.println("User added: " + user.getFullName());
    }

    public void removeUser(User user) {
        boolean removed = DataStorage.getInstance().getUsers().remove(user);

        if (removed) {
            log("REMOVE USER: " + user.getLogin());
            System.out.println("User removed: " + user.getFullName());
        } else {
            System.out.println("User not found: " + user.getLogin());
        }
    }

    public void updateUser(User user) {
      
    }

    public void viewLogs() {
      
    }

    private void log(String action) {
        
    }

    @Override
    public String getRole() {
        return "Admin";
    }

    @Override
    public String toString() {
        return "Admin | id: " + getId() + " | name: " + getFullName() + " | department: " + getDepartment();
    }
}