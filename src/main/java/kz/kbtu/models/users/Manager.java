package kz.kbtu.models.users;

import kz.kbtu.models.academic.Course;
import kz.kbtu.patterns.EventManager;

import java.io.Serializable;

/**
 * Manager employee — approves registrations, assigns teachers, manages news.
 */
public class Manager extends Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    private ManagerType managerType;

    public Manager() {}

    public Manager(String username, String password, String firstName, String lastName, String id,
                   String employeeId, String department, ManagerType managerType) {
        super(username, password, firstName, lastName, id, employeeId, department);
        this.managerType = managerType;
    }

    public void approveRegistration(Student student, Course course) {
        EventManager.getInstance().notify(
                "Manager " + fullName() + " approved " + student.fullName() + " for " + course.getCourseId());
    }

    public void assignTeacher(Teacher teacher, Course course) {
        teacher.manageCourse(course);
        EventManager.getInstance().notify(
                "Manager " + fullName() + " assigned " + teacher.fullName() + " to " + course.getCourseId());
    }

    public void viewReports() {
        // Delegates to ReportService at call sites; here we just log the action
        EventManager.getInstance().notify("Manager " + fullName() + " viewed reports");
    }

    public void manageNews(String headline) {
        kz.kbtu.patterns.Database.getInstance().getNews().add(headline);
        EventManager.getInstance().notify("News added by " + fullName() + ": " + headline);
    }

    public ManagerType getManagerType() { return managerType; }
    public void setManagerType(ManagerType managerType) { this.managerType = managerType; }

    @Override
    public String toString() {
        return "Manager{" + fullName() + ", type=" + managerType + ", dept=" + department + "}";
    }
}
