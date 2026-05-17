package kz.kbtu.patterns;

import kz.kbtu.models.academic.Course;
import kz.kbtu.models.academic.Mark;
import kz.kbtu.models.research.ResearchPaper;
import kz.kbtu.models.research.ResearchProject;
import kz.kbtu.models.users.Student;
import kz.kbtu.models.users.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Singleton in-memory database. Serializable for disk persistence.
 *
 * <p>This is one of the project's design patterns (Singleton). Holds the entire system state
 * so demo runs can be saved/restored.</p>
 */
public class Database implements Serializable {
    private static final long serialVersionUID = 1L;
    private static Database instance;

    private final List<User> users = new ArrayList<>();
    private final List<Course> courses = new ArrayList<>();
    private final List<Mark> marks = new ArrayList<>();
    private final List<ResearchPaper> papers = new ArrayList<>();
    private final List<ResearchProject> projects = new ArrayList<>();
    private final List<String> news = new ArrayList<>();

    private Database() {}

    public static synchronized Database getInstance() {
        if (instance == null) instance = new Database();
        return instance;
    }

    public List<User> getUsers() { return users; }
    public List<Course> getCourses() { return courses; }
    public List<Mark> getMarks() { return marks; }
    public List<ResearchPaper> getPapers() { return papers; }
    public List<ResearchProject> getProjects() { return projects; }
    public List<String> getNews() { return news; }

    public Optional<User> findUser(String username) {
        return users.stream().filter(u -> u.getUsername().equals(username)).findFirst();
    }

    public Optional<Mark> findMark(Student student, Course course) {
        return marks.stream()
                .filter(m -> m.getStudent().equals(student) && m.getCourse().equals(course))
                .findFirst();
    }

    /** Save current state to a file. */
    public void save(String path) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(this);
        }
    }

    /** Load state from file, replacing the singleton. Returns the loaded instance. */
    public static Database load(String path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            Database loaded = (Database) ois.readObject();
            instance = loaded;
            return loaded;
        }
    }

    /** Reset for fresh demo runs. */
    public static synchronized void reset() {
        instance = new Database();
        EventManager.getInstance().clear();
    }

    @Override
    public String toString() {
        return "Database{users=" + users.size() + ", courses=" + courses.size() +
                ", marks=" + marks.size() + ", papers=" + papers.size() +
                ", projects=" + projects.size() + ", news=" + news.size() + "}";
    }
}
