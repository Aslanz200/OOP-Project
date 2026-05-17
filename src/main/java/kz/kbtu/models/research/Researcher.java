package kz.kbtu.models.research;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

public interface Researcher extends Serializable {
    int getHIndex();
    void setHIndex(int hIndex);

    List<ResearchPaper> getPapers();
    List<ResearchProject> getProjects();

    void addPaper(ResearchPaper paper);

    void joinProject(ResearchProject project);

    void printPapers(Comparator<ResearchPaper> comparator);

    String identity();
}
