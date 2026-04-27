package model.users;

import model.communication.Message;
import model.communication.Request;
import enums.RequestStatus;
import enums.UrgencyLevel;

import java.time.LocalDate;

public abstract class Employee extends User {

    private static final long serialVersionUID = 1L;

    private double salary;
    private String department;
    private String position;
    private LocalDate hiringDate;

    public Employee(String firstName, String lastName, String login, String password, double salary, String department, String position, LocalDate hiringDate) {
        super(firstName, lastName, login, password);
        this.salary = salary;
        this.department = department;
        this.position = position;
        this.hiringDate = hiringDate;
    }

    public Message sendMessage(Employee receiver, String text) {
        Message message = new Message(this, receiver, text);
        System.out.println("Message sent to " + receiver.getFullName());
        return message;
    }

    public Request sendRequest(String description) {
        Request request = new Request(this, description, UrgencyLevel.MEDIUM); //по умолчанию заявка medium
        System.out.println("Request sent: " + description);
        return request;
    }

    public Request sendComplaint(String text) {
        Request complaint = new Request(this, text, UrgencyLevel.HIGH);// как и Request только HIGH
        complaint.updateStatus(RequestStatus.NEW);
        System.out.println("Complaint sent: " + text);
        return complaint;
    }

    public double getSalary() { return salary; }
    public String getDepartment() { return department; }
    public String getPosition() { return position; }
    public LocalDate getHiringDate() { return hiringDate; }

    public void setSalary(double salary) { this.salary = salary; }
    public void setDepartment(String department) { this.department = department; }
    public void setPosition(String position) { this.position = position; }
    public void setHiringDate(LocalDate hiringDate) { this.hiringDate = hiringDate; }

    @Override
    public String toString() {
        return getRole() + " | id: " + getId() + " | name: " + getFullName() + " | department: " + department + " | position: " + position + " | salary: " + salary;
    }
}