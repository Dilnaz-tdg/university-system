package core;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import model.academic.Report;
import model.communication.News;
import model.communication.NewsTopic;
import model.communication.Request;
import model.research.ResearchPaper;
import model.users.Manager;
import model.users.ManagerType;
import model.users.TechSupportSpecialist;
import notification.Journal;


public class Main {
    public static void main(String[] args){
        Manager manager = new Manager(
                "Aruzhan",
                "Sarsen",
                "manager1",
                "12345",
                350000,
                "Dean Office",
                "Manager",
                LocalDate.of(2022, 9, 1),
                ManagerType.DEPARTMENT
        );
        TechSupportSpecialist support = new TechSupportSpecialist(
                "Nursultan",
                "Aidar",
                "support1",
                "12345",
                220000,
                "IT Support",
                "Specialist",
                LocalDate.of(2023, 2, 10)
        );
        Request request = manager.sendRequest("Projector in room 301 does not work.");
        support.getAssignedRequests().add(request);
        System.out.println("New requests:");
        System.out.println(support.viewNewRequests());
        support.acceptRequest(request);
        System.out.println("Updated request:");
        System.out.println(request);
        News news = new News(
                "Research Week",
                "Research week starts next Monday.",
                NewsTopic.RESEARCH,
                manager
        );
        news.addComment("Published for all employees.");
        news.pin();

        System.out.println("News object:");
        System.out.println(news);

        Journal journal = new Journal("University Research Journal");
        journal.subscribe(manager);
        journal.subscribe(support);
        ResearchPaper paper = new ResearchPaper(
                "AI in Education",
                List.of("A. Smith", "B. Lee"),
                "University Research Journal",
                12,
                "10.1000/ai-edu-2026",
                5,
                LocalDate.now()
        );
        journal.publishPaper(paper);
        System.out.println("Journal object:");
        System.out.println(journal);
        Report report = new Report(
                "Communication Module Report",
                Map.of(
                        "OpenRequests", 1.0,
                        "PinnedNews", 1.0
                )
        );
        report.generate();
        report.print();
        DataStorage.getInstance().addRequest(request);
        DataStorage.getInstance().addNews(news);
        DataStorage.getInstance().addJournal(journal);

        System.out.println(DataStorage.getInstance());
    }
}
