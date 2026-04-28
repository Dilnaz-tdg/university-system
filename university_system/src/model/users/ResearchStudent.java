package model.users;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.research.ResearchPaper;
import model.research.ResearchProject;
import model.research.Researcher;
public class ResearchStudent extends Student implements Researcher {

    private List<ResearchPaper> papers = new ArrayList<>();
    private List<ResearchProject> projects = new ArrayList<>();
    private int hIndex;

    public ResearchStudent(String firstName, String lastName, String login, String password, String major, int yearOfStudy){
        super(firstName, lastName, login, password, major, yearOfStudy);
        this.hIndex = 0;
    }

    @Override
    public int calculateHIndex(){
        List<Integer> citations = new ArrayList<>();

        for(ResearchPaper paper: papers){
            citations.add(paper.getCitations());
        }

        citations.sort(Comparator.reverseOrder());

        int h = 0;
        for(int i = 0 ; i < citations.size();i++){
            if(citations.get(i) >= i + 1){
                h = i + 1;
            }else{
                break;
            }
        }

        this.hIndex = h;
        return hIndex;
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> comparator){
        List<ResearchPaper> sortedPapers = new ArrayList<>(papers);
        sortedPapers.sort(comparator);

        for(ResearchPaper paper : sortedPapers){
            System.out.println(paper);
        }
    }

    @Override
    public List<ResearchPaper> getResearchPapers() { return papers; }

    @Override
    public List<ResearchProject> getResearchProjects() { return projects; }


    @Override
    public void addPaper(ResearchPaper paper){
        if(paper!=null){
            papers.add(paper);
        }
    }

    @Override
    public void addProject(ResearchProject project){
        if(project!=null && !projects.contains(project)){
            projects.add(project);
        }
    }

    @Override
    public String getRole() { return "Research Student"; }

    public int getHIndex() { return calculateHIndex(); }





}
