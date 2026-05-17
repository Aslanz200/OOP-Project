package kz.kbtu.models.research;

import kz.kbtu.models.users.Employee;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ResearcherDecorator implements Researcher, Serializable {
    private static final long serialVersionUID = 1L;

    private final Employee employee;
    private int hIndex;
    private final List<ResearchPaper> papers = new ArrayList<>();
    private final List<ResearchProject> projects = new ArrayList<>();

    public ResearcherDecorator(Employee employee, int hIndex) {
        this.employee = employee;
        this.hIndex = hIndex;
    }

    public Employee getEmployee() { return employee; }

    @Override
    public int getHIndex() { return hIndex; }

    @Override
    public void setHIndex(int hIndex) { this.hIndex = hIndex; }

    @Override
    public List<ResearchPaper> getPapers() { return papers; }

    @Override
    public List<ResearchProject> getProjects() { return projects; }

    @Override
    public void addPaper(ResearchPaper paper) { papers.add(paper); }

    @Override
    public void joinProject(ResearchProject project) {
        try {
            project.addParticipant(this);
            projects.add(project);
        } catch (Exception e) {

            throw new IllegalStateException(e);
        }
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> comparator) {
        System.out.println("== Papers of " + identity() + " (h-index=" + hIndex + ") ==");
        papers.stream().sorted(comparator).forEach(p -> System.out.println("  " + p));
    }

    @Override
    public String identity() {
        return employee.fullName() + " (" + employee.getClass().getSimpleName() + ")";
    }

    @Override
    public String toString() {
        return "ResearcherDecorator{" + identity() + ", h=" + hIndex +
                ", papers=" + papers.size() + ", projects=" + projects.size() + "}";
    }
}
