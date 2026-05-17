package kz.kbtu.models.research;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

/**
 * A Researcher mixin. Implemented either directly by an Employee/Student or
 * via the {@link ResearcherDecorator} pattern.
 *
 * <p>Per spec: Teachers and Students CAN be researchers; Professors ARE always researchers.</p>
 */
public interface Researcher extends Serializable {
    int getHIndex();
    void setHIndex(int hIndex);

    List<ResearchPaper> getPapers();
    List<ResearchProject> getProjects();

    /** Add a paper authored by this researcher. */
    void addPaper(ResearchPaper paper);

    /** Join a research project (must already be a Researcher — enforced inside ResearchProject). */
    void joinProject(ResearchProject project);

    /**
     * Strategy-pattern method: prints this researcher's papers in the order
     * dictated by the supplied {@link Comparator}.
     */
    void printPapers(Comparator<ResearchPaper> comparator);

    /** Human-readable identity for logs/printouts. */
    String identity();
}
