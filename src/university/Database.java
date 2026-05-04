package university;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private static Database instance;

    private List<User> users;
    private List<Course> courses;
    private List<ResearchPaper> papers;
    private List<ResearchProject> projects;

    private Database() {
        this.users = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.papers = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    public static Database getInstance() {
        // TODO: consider thread safety (double-checked locking) if multi-threaded access is required
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
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

    @Override
    public String toString() {
        return "Database{" +
                "users=" + users.size() +
                ", courses=" + courses.size() +
                ", papers=" + papers.size() +
                ", projects=" + projects.size() +
                '}';
    }
}
