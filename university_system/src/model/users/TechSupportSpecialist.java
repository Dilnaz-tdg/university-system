package model.users;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import core.Employee;
import model.communication.Request;
import model.communication.RequestStatus;

public class TechSupportSpecialist extends Employee{
    private List<Request> assignedRequests;
    public TechSupportSpecialist(String firstName, String lastName, String login, String password,
                                 double salary, String department, String position, LocalDate hiringDate) {
        super(firstName, lastName, login, password, salary, department, position, hiringDate);
        this.assignedRequests = new ArrayList<>();
    }

    public List<Request> getAssignedRequests() {
        return assignedRequests;
    }
    // Возвращаем только новые заявки, которые ещё не были обработаны.
    public List<Request> viewNewRequests(){
        List<Request> newRequests = new ArrayList<>();
        for(Request request : assignedRequests){
            if(request.getStatus() == RequestStatus.NEW){
                newRequests.add(request);
            }
        }
        return newRequests;
    }
    // При принятии заявки меняем её статус на ACCEPTED.
    public void acceptRequest(Request request){
        if(request!= null){
            request.updateStatus(RequestStatus.ACCEPTED);
        }
    }
    // При отклонении заявки меняем её статус на REJECTED.
    public void rejectRequest(Request request){
        if(request != null){
            request.updateStatus(RequestStatus.REJECTED);
        }
    }
    @Override
    public String getRole() {
        return "Tech Support Specialist";
    }

    @Override
    public String toString() {
        return "TechSupportSpecialist{" +
                "assignedRequests=" + assignedRequests +
                '}';
    }
}