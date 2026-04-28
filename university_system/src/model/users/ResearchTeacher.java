package model.users;

import model.research.ResearchPaper;
import model.research.ResearchProject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import enums.TeacherPosition;

public class ResearchTeacher extends Teacher implements Researcher {

    private static final long serialVersionUID = 1L;

    private List<ResearchPaper> papers;
    private List<ResearchProject> projects;
    private int hIndex;

    public ResearchTeacher(String firstName, String lastName, String login, String password,
                           double salary, String department, String position, LocalDate hiringDate,
                           TeacherPosition positionTitle) {
        super(firstName, lastName, login, password, salary, department, position, hiringDate, positionTitle);

        if (positionTitle == TeacherPosition.PROFESSOR) {
            throw new IllegalArgumentException("ResearchTeacher cannot have PROFESSOR position. Use Professor class instead.");
        }

        this.papers = new ArrayList<>();
        this.projects = new ArrayList<>();
        this.hIndex = 0;
    }

    @Override
    public int calculateHIndex() {
        int h = 0;

        for (int i = 0; i < papers.size(); i++) {
            int count = 0;

            for (ResearchPaper paper : papers) {
                if (paper.getCitations() >= i + 1) {
                    count++;
                }
            }

            if (count >= i + 1) {
                h = i + 1;
            }
        }

        this.hIndex = h;
        return hIndex;
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> comparator) {
        List<ResearchPaper> sortedPapers = new ArrayList<>(papers);
        sortedPapers.sort(comparator);

        for (ResearchPaper paper : sortedPapers) {
            System.out.println(paper);
        }
    }

    @Override
    public List<ResearchPaper> getResearchPapers() {
        return papers;
    }

    @Override
    public List<ResearchProject> getResearchProjects() {
        return projects;
    }

    @Override
    public void addPaper(ResearchPaper paper) {
        papers.add(paper);
        calculateHIndex();
    }

    @Override
    public void addProject(ResearchProject project) {
        projects.add(project);
    }

    public int getHIndex() {
        return hIndex;
    }

    @Override
    public String getRole() {
        return "Research Teacher";
    }

    @Override
    public String toString() {
        return "ResearchTeacher | id: " + getId() + " | name: " + getFullName() + " | position: " + getPosition() + " | h-index: " + hIndex;
    }
}