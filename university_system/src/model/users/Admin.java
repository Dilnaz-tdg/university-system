package model.users;

import patterns.DataStorage;
import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

public class Admin extends Employee {

    private static final long serialVersionUID = 1L;
    private static final String LOG_FILE = "logs/actions.log";

    public Admin(String firstName, String lastName, String login, String password,
                 double salary, String department, String position, java.time.LocalDate hiringDate) {
        super(firstName, lastName, login, password, salary, department, position, hiringDate);
    }

    public void addUser(User user) {
        DataStorage.getInstance().getUsers().add(user);
        log("ADD USER: " + user.getLogin());
        System.out.println("User added: " + user.getFirstName() + " " + user.getLastName());
    }

    public void removeUser(User user) {
        boolean removed = DataStorage.getInstance().getUsers().remove(user);
        if (removed) {
            log("REMOVE USER: " + user.getLogin());
            System.out.println("User removed: " + user.getFirstName() + " " + user.getLastName());
        } else {
            System.out.println("User not found: " + user.getLogin());
        }
    }

    public void updateUser(User user) {
        List<User> users = DataStorage.getInstance().getUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(user.getId())) {
                users.set(i, user);
                log("UPDATE USER: " + user.getLogin());
                System.out.println("User updated: " + user.getFirstName() + " " + user.getLastName());
                return;
            }
        }
        System.out.println("User not found: " + user.getLogin());
    }

    public void viewLogs() {
        File file = new File(LOG_FILE);
        if (!file.exists()) {
            System.out.println("Log file is empty.");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            System.out.println("===== ACTION LOGS =====");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("=======================");
        } catch (IOException e) {
            System.out.println("Error reading log file: " + e.getMessage());
        }
    }

    private void log(String action) {
        File dir = new File("logs");
        if (!dir.exists()) dir.mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write("[" + LocalDateTime.now() + "] " + getLogin() + " -> " + action);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing log: " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "\nAdmin: " + getId() + " | name: " + getFirstName() + " " + getLastName() + " | department: " + getDepartment();
    }
}