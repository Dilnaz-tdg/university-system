package model.users;

import enums.RequestStatus;
import java.util.ArrayList;
import java.util.List;
import model.communication.Request;

public class TechSupportSpecialist extends Employee {
    private List<Request> assignedRequests;

    public TechSupportSpecialist() {
        this.assignedRequests = new ArrayList<>();
    }

    public List<Request> getAssignedRequests() {
        return assignedRequests;
    }

    // Возвращаем только новые заявки, которые ещё не были обработаны.
    public List<Request> viewNewRequests() {
        List<Request> newRequests = new ArrayList<>();

        for (Request request : assignedRequests) {
            if (request.getStatus() == RequestStatus.NEW) {
                newRequests.add(request);
            }
        }

        return newRequests;
    }

    // При принятии заявки меняем её статус на ACCEPTED.
    public void acceptRequest(Request request) {
        if (request != null) {
            request.updateStatus(RequestStatus.ACCEPTED);
        }
    }

    // При отклонении заявки меняем её статус на REJECTED.
    public void rejectRequest(Request request) {
        if (request != null) {
            request.updateStatus(RequestStatus.REJECTED);
        }
    }

    @Override
    public String toString() {
        return "TechSupportSpecialist{" +
                "assignedRequests=" + assignedRequests +
                '}';
    }
}
