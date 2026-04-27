package model.research;

import java.util.Comparator;
import java.util.List;

public interface Researcher {
    int calculateHIndex(); // h index , h states = named h times

    void printPapers(Comparator<ResearchPaper> comparator);

    List<ResearchPaper> getResearchPapers();
    List<ResearchProject> getResearchProjects();

    void addPaper(ResearchPaper paper);

    void addProject(ResearchProject project);
}