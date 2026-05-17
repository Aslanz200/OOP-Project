package kz.kbtu.models.research;

import kz.kbtu.exceptions.NotResearcherException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResearchProject implements Serializable {
    private static final long serialVersionUID = 1L;

    private String topic;
    private List<Researcher> participants = new ArrayList<>();
    private List<ResearchPaper> papers = new ArrayList<>();

    public ResearchProject() {}

    public ResearchProject(String topic) {
        this.topic = topic;
    }

    public void addParticipant(Object candidate) throws NotResearcherException {
        if (!(candidate instanceof Researcher)) {
            throw new NotResearcherException(
                    "Cannot add " + candidate.getClass().getSimpleName() +
                            " to project '" + topic + "': not a Researcher");
        }
        participants.add((Researcher) candidate);
    }

    public void addPaper(ResearchPaper paper) {
        papers.add(paper);
    }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public List<Researcher> getParticipants() { return participants; }
    public List<ResearchPaper> getPapers() { return papers; }

    @Override
    public String toString() {
        return "ResearchProject{" + topic + ", participants=" + participants.size() +
                ", papers=" + papers.size() + "}";
    }
}
