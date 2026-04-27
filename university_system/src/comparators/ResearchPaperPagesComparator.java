package comparators;

import java.util.Comparator;
import model.research.ResearchPaper;

public class ResearchPaperPagesComparator implements Comparator<ResearchPaper> {

    @Override
    public int compare(ResearchPaper p1, ResearchPaper p2){ return Integer.compare(p2.getPages(), p1.getPages()); }

}
