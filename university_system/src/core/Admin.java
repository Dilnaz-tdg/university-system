package core;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Admin extends Employee {

    private static final long serialVersionUID = 1L;
    private static final String LOG_FILE = "logs/actions.log";

    public Admin(String firstName, String lastName, String login, String password,
                 double salary, String department, String position, LocalDate hiringDate) {
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

    public void updateUser(User updatedUser) {
        List<User> users = DataStorage.getInstance().getUsers();

        for (int i = 0; i < users.size(); i++) {
            User currentUser = users.get(i);

            if (currentUser.equals(updatedUser)) {
                users.set(i, updatedUser);
                log("UPDATE USER: " + updatedUser.getLogin());
                System.out.println("User updated: " + updatedUser.getFullName());
                return;
            }
        }

        System.out.println("User not found: " + updatedUser.getLogin());
    }

    public void viewLogs() {
        File file = new File(LOG_FILE);

        if (!file.exists()) {
            System.out.println("No logs found.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            System.out.println("Error while reading logs: " + e.getMessage());
        }
    }

    private void log(String action) {
        File file = new File(LOG_FILE);
        File folder = file.getParentFile();

        if (folder != null && !folder.exists()) {
            folder.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(LocalDateTime.now() + " | ADMIN: " + getLogin() + " | " + action);
            writer.newLine();

        } catch (IOException e) {
            System.out.println("Error while writing log: " + e.getMessage());
        }
    }

    @Override
    public String getRole() {
        return "Admin";
    }

    @Override
    public String toString() {
        return "Admin | id: " + getId()
                + " | name: " + getFullName()
                + " | department: " + getDepartment();
    }
}