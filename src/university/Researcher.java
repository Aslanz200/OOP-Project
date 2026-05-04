package university;

import java.util.ArrayList;
import java.util.List;

public class Researcher {
    private int hIndex;
    private List<ResearchPaper> papers;
    private List<ResearchProject> projects;

    public Researcher() {
        this.papers = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    public Researcher(int hIndex) {
        this.hIndex = hIndex;
        this.papers = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    public int getHIndex() {
        return hIndex;
    }

    public void setHIndex(int hIndex) {
        this.hIndex = hIndex;
    }

    public List<ResearchPaper> getPapers() {
        return papers;
    }

    public void setPapers(List<ResearchPaper> papers) {
        this.papers = papers;
    }

    public List<ResearchProject> getProjects() {
        return projects;
    }

    public void setProjects(List<ResearchProject> projects) {
        this.projects = projects;
    }

    public String getId() {
        // TODO: return identifier; consider linking to a User account if Researcher is paired with a person
        return null;
    }

    public void displayPapers() {
        // TODO: implement listing of papers (sorted by citations or date)
    }

    public void addPaper(ResearchPaper paper) {
        // TODO: implement add-paper logic; update hIndex calculation
    }

    @Override
    public String toString() {
        return "Researcher{" +
                "hIndex=" + hIndex +
                ", papers=" + papers.size() +
                ", projects=" + projects.size() +
                '}';
    }
}
