package model.research;

import java.util.ArrayList;
import java.util.List;

import exceptions.NotResearcherException;

public class ResearchProject {
	
    private String topic;
    private List<ResearchPaper> papers;
    private List<Researcher> participants;

    public ResearchProject(String topic){
        this.topic = topic;
        this.papers = new ArrayList<>();
        this.participants = new ArrayList<>();
    }

    public String getTopic(){ return topic; }

    public List<ResearchPaper> getPapers(){ return papers; }

    public List<Researcher> getParticipant(){ return participants; }

    public void addParticipant(Researcher researcher){
        if(researcher == null){
            throw new IllegalArgumentException("Participant must be a researcher");
        }

        if(!participants.contains(researcher)){
            participants.add(researcher);
            researcher.addProject(this);
        }
    }

    public void addParticipant(Object user) throws NotResearcherException {

        if (!(user instanceof Researcher)) {
    
            throw new NotResearcherException("Only researchers can join research project");
    
        }
    
        addParticipant((Researcher) user);
    
    }


    public void addPaper(ResearchPaper paper){
        if(paper == null){
            throw new IllegalArgumentException("Paper can't be null");
        }

        papers.add(paper);
    }

    public void publishPaper(ResearchPaper paper) { addPaper(paper); }

    @Override

    public String toString() {
        return "ResearchProject{" +
                "topic='" + topic + '\'' +
                ", papers=" + papers.size() +
                ", participants=" + participants.size() +
                '}';
    }


}
