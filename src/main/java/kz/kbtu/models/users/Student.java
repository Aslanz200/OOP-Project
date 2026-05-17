package kz.kbtu.models.users;

import kz.kbtu.exceptions.CreditLimitException;
import kz.kbtu.exceptions.LowHIndexException;
import kz.kbtu.models.academic.Course;
import kz.kbtu.models.academic.Mark;
import kz.kbtu.models.research.Researcher;
import kz.kbtu.patterns.Database;
import kz.kbtu.patterns.EventManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Bachelor student. Carries year (1..4), GPA, credits, fail count, enrolled courses,
 * and (for 4th years) a research supervisor.
 */
public class Student extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final int MAX_CREDITS = 21;
    public static final int MAX_FAILS = 3;
    public static final int MIN_SUPERVISOR_H_INDEX = 3;

    private int year;
    private double gpa;
    private int credits;
    private int failCount;
    private List<Course> courses = new ArrayList<>();
    private Researcher supervisor;

    public Student() {}

    public Student(String username, String password, String firstName, String lastName, String id,
                   int year, double gpa) {
        super(username, password, firstName, lastName, id);
        this.year = year;
        this.gpa = gpa;
        this.credits = 0;
        this.failCount = 0;
    }

    /**
     * Enrolls the student in a course, enforcing the 21-credit ceiling and the 3-fail cap.
     * @throws CreditLimitException if the new total would exceed the limit or fail cap is exceeded
     */
    public void registerForCourse(Course course) throws CreditLimitException {
        if (failCount >= MAX_FAILS) {
            throw new CreditLimitException(
                    "Student " + fullName() + " has " + failCount + " fails (>= " + MAX_FAILS + "); cannot register");
        }
        if (credits + course.getCredits() > MAX_CREDITS) {
            throw new CreditLimitException(
                    "Student " + fullName() + " cannot exceed " + MAX_CREDITS +
                            " credits (tried " + (credits + course.getCredits()) + ")");
        }
        if (!courses.contains(course)) {
            courses.add(course);
            course.getStudents().add(this);
            credits += course.getCredits();
            EventManager.getInstance().notify(
                    "Student " + fullName() + " registered for " + course.getCourseId());
        }
    }

    public List<Mark> viewGrades() {
        return Database.getInstance().getMarks().stream()
                .filter(m -> m.getStudent().equals(this))
                .collect(Collectors.toList());
    }

    public void viewTranscript() {
        List<Mark> marks = viewGrades();
        System.out.println("=== Transcript: " + fullName() + " ===");
        for (Mark m : marks) {
            System.out.printf("  %s | att1=%.1f att2=%.1f final=%.1f total=%.1f grade=%s%n",
                    m.getCourse().getName(),
                    m.getAttestation1(), m.getAttestation2(), m.getFinalExam(),
                    m.getTotal(), m.getLetterGrade());
        }
        System.out.printf("Current GPA: %.2f, credits: %d, fails: %d%n", gpa, credits, failCount);
    }

    public void rateTeacher(Teacher teacher, double rating) {
        EventManager.getInstance().notify(
                "Student " + fullName() + " rated teacher " + teacher.fullName() + " : " + rating);
    }

    /**
     * Assigns research supervisor. Throws if h-index < 3 as per spec.
     */
    public void setSupervisor(Researcher supervisor) throws LowHIndexException {
        if (supervisor.getHIndex() < MIN_SUPERVISOR_H_INDEX) {
            throw new LowHIndexException(
                    "Supervisor h-index = " + supervisor.getHIndex() + " < " + MIN_SUPERVISOR_H_INDEX);
        }
        this.supervisor = supervisor;
        EventManager.getInstance().notify(
                "Student " + fullName() + " assigned supervisor with h-index " + supervisor.getHIndex());
    }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public double getGpa() { return gpa; }
    public void setGpa(double gpa) { this.gpa = gpa; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }

    public int getFailCount() { return failCount; }
    public void setFailCount(int failCount) { this.failCount = failCount; }
    public void incrementFails() { this.failCount++; }

    public List<Course> getCourses() { return courses; }
    public void setCourses(List<Course> courses) { this.courses = courses; }

    public Researcher getSupervisor() { return supervisor; }

    @Override
    public String toString() {
        return "Student{" + fullName() + ", year=" + year + ", gpa=" + gpa +
                ", credits=" + credits + ", fails=" + failCount + "}";
    }
}
