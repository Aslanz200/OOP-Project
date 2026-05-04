package university;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResearchPaper {
    private String title;
    private List<String> author;
    private String journal;
    private int pages;
    private Date datePublished;
    private int citations;
    private String doi;

    public ResearchPaper() {
        this.author = new ArrayList<>();
    }

    public ResearchPaper(String title, List<String> author, String journal, int pages,
                         Date datePublished, int citations, String doi) {
        this.title = title;
        this.author = author;
        this.journal = journal;
        this.pages = pages;
        this.datePublished = datePublished;
        this.citations = citations;
        this.doi = doi;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Date getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
    }

    public int getCitations() {
        return citations;
    }

    public void setCitations(int citations) {
        this.citations = citations;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    @Override
    public String toString() {
        return "ResearchPaper{" +
                "title='" + title + '\'' +
                ", author=" + author +
                ", journal='" + journal + '\'' +
                ", pages=" + pages +
                ", datePublished=" + datePublished +
                ", citations=" + citations +
                ", doi='" + doi + '\'' +
                '}';
    }
}
