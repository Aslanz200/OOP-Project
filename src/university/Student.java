package university;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private int year;
    private double gpa;
    private int credits;
    private int failCount;
    private List<Course> courses;
    private Researcher supervisor;

    public Student() {
        super();
        this.courses = new ArrayList<>();
    }

    public Student(String username, String password, String firstName, String lastName, String id,
                   int year, double gpa, int credits) {
        super(username, password, firstName, lastName, id);
        setYear(year);
        this.gpa = gpa;
        this.credits = credits;
        this.failCount = 0;
        this.courses = new ArrayList<>();
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        // TODO: validate year is between 1 and 4
        this.year = year;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getFailCount() {
        return failCount;
    }

    public void setFailCount(int failCount) {
        // TODO: enforce maximum failCount of 3 (expulsion logic)
        this.failCount = failCount;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public Researcher getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Researcher supervisor) {
        // TODO: implement supervisor assignment logic
        this.supervisor = supervisor;
    }

    public void register() {
        // TODO: implement course registration logic
    }

    public void viewGrades() {
        // TODO: implement viewing grades for current semester
    }

    public void viewTranscript() {
        // TODO: implement transcript viewing across all semesters
    }

    @Override
    public String toString() {
        return "Student{" +
                "year=" + year +
                ", gpa=" + gpa +
                ", credits=" + credits +
                ", failCount=" + failCount +
                ", courses=" + courses +
                ", supervisor=" + (supervisor != null ? supervisor.getId() : "none") +
                ", " + super.toString() +
                '}';
    }
}
