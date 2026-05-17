package kz.kbtu.models.users;

import kz.kbtu.models.academic.Course;
import kz.kbtu.models.academic.Mark;
import kz.kbtu.patterns.Database;
import kz.kbtu.patterns.EventManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Teacher employee. Holds a title (tutor/lector/senior_lector/professor) and a list of courses they instruct.
 */
public class Teacher extends Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    private TeacherTitle title;
    private List<Course> courses = new ArrayList<>();

    public Teacher() {}

    public Teacher(String username, String password, String firstName, String lastName, String id,
                   String employeeId, String department, TeacherTitle title) {
        super(username, password, firstName, lastName, id, employeeId, department);
        this.title = title;
    }

    /** Assigns a numerical mark to a student for a given course (final exam score by default). */
    public void putMark(Student student, Course course, double value) {
        Mark mark = Database.getInstance().findMark(student, course)
                .orElseGet(() -> {
                    Mark m = new Mark(student, course, 0, 0, 0);
                    Database.getInstance().getMarks().add(m);
                    return m;
                });
        mark.setFinalExam(value);
        EventManager.getInstance().notify(
                "Teacher " + fullName() + " set finalExam=" + value + " for " + student.fullName() +
                        " in " + course.getCourseId());
    }

    /** Returns students enrolled in the given course. */
    public List<Student> viewStudents(Course course) {
        return new ArrayList<>(course.getStudents());
    }

    public void manageCourse(Course course) {
        if (!courses.contains(course)) courses.add(course);
        if (!course.getTeachers().contains(this)) course.getTeachers().add(this);
        EventManager.getInstance().notify(
                "Teacher " + fullName() + " now manages " + course.getCourseId());
    }

    public void sendMessage(Employee employee, String text) {
        EventManager.getInstance().notify(
                "Message " + fullName() + " -> " + employee.fullName() + ": " + text);
    }

    public TeacherTitle getTitle() { return title; }
    public void setTitle(TeacherTitle title) { this.title = title; }

    public List<Course> getCourses() { return courses; }
    public void setCourses(List<Course> courses) { this.courses = courses; }

    @Override
    public String toString() {
        return "Teacher{" + fullName() + ", title=" + title + ", department=" + department + "}";
    }
}
