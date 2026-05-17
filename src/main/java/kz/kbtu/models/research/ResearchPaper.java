package kz.kbtu.models.research;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * A research paper. Fields chosen from the IEEE article fields list per spec:
 * title, authors, journal, pages, datePublished, citations, doi.
 *
 * <p>Implements Comparable (by citations, descending) and supplies three named Comparators
 * for the Strategy pattern used in {@link Researcher#printPapers(Comparator)}.</p>
 */
public class ResearchPaper implements Serializable, Comparable<ResearchPaper> {
    private static final long serialVersionUID = 1L;

    private String title;
    private List<String> authors = new ArrayList<>();
    private String journal;
    private int pages;
    private Date datePublished;
    private int citations;
    private String doi;

    public ResearchPaper() {}

    public ResearchPaper(String title, List<String> authors, String journal, int pages,
                         Date datePublished, int citations, String doi) {
        this.title = title;
        this.authors = authors;
        this.journal = journal;
        this.pages = pages;
        this.datePublished = datePublished;
        this.citations = citations;
        this.doi = doi;
    }

    /** Natural order: most-cited first. */
    @Override
    public int compareTo(ResearchPaper other) {
        return Integer.compare(other.citations, this.citations);
    }

    public static final Comparator<ResearchPaper> BY_DATE =
            Comparator.comparing(ResearchPaper::getDatePublished,
                    Comparator.nullsLast(Comparator.naturalOrder())).reversed();

    public static final Comparator<ResearchPaper> BY_CITATIONS =
            Comparator.comparingInt(ResearchPaper::getCitations).reversed();

    public static final Comparator<ResearchPaper> BY_PAGES =
            Comparator.comparingInt(ResearchPaper::getPages).reversed();

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public List<String> getAuthors() { return authors; }
    public void setAuthors(List<String> authors) { this.authors = authors; }

    public String getJournal() { return journal; }
    public void setJournal(String journal) { this.journal = journal; }

    public int getPages() { return pages; }
    public void setPages(int pages) { this.pages = pages; }

    public Date getDatePublished() { return datePublished; }
    public void setDatePublished(Date datePublished) { this.datePublished = datePublished; }

    public int getCitations() { return citations; }
    public void setCitations(int citations) { this.citations = citations; }

    public String getDoi() { return doi; }
    public void setDoi(String doi) { this.doi = doi; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResearchPaper)) return false;
        return Objects.equals(doi, ((ResearchPaper) o).doi);
    }

    @Override
    public int hashCode() { return Objects.hash(doi); }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "Paper{\"" + title + "\" by " + authors + ", " + journal +
                ", " + pages + "p, " + (datePublished != null ? sdf.format(datePublished) : "n/a") +
                ", cited=" + citations + ", doi=" + doi + "}";
    }
}
