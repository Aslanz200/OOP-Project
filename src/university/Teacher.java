package university;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Employee {
    private TeacherTitle title;
    private List<Course> courses;

    public Teacher() {
        super();
        this.courses = new ArrayList<>();
    }

    public Teacher(String username, String password, String firstName, String lastName, String id,
                   String employeeID, String department, TeacherTitle title) {
        super(username, password, firstName, lastName, id, employeeID, department);
        this.title = title;
        this.courses = new ArrayList<>();
    }

    public TeacherTitle getTitle() {
        return title;
    }

    public void setTitle(TeacherTitle title) {
        this.title = title;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void putMark(Student student, Course course, double attestation1, double attestation2, double finalExam) {
        // TODO: implement mark assignment logic; create/update Mark for student in course
    }

    public void viewStudents() {
        // TODO: implement listing of all students enrolled in this teacher's courses
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "title=" + title +
                ", courses=" + courses +
                ", " + super.toString() +
                '}';
    }
}
