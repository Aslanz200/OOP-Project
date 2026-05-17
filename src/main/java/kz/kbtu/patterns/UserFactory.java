package kz.kbtu.patterns;

import kz.kbtu.models.users.Admin;
import kz.kbtu.models.users.Manager;
import kz.kbtu.models.users.ManagerType;
import kz.kbtu.models.users.Student;
import kz.kbtu.models.users.Teacher;
import kz.kbtu.models.users.TeacherTitle;
import kz.kbtu.models.users.User;

/**
 * Factory pattern. Constructs a concrete {@link User} subclass based on the given type string.
 * Centralises object creation so demo code can stay role-agnostic.
 */
public final class UserFactory {

    private UserFactory() {}

    /**
     * Creates a user.
     *
     * @param type one of "student", "teacher", "manager", "admin" (case-insensitive)
     * @param args positional arguments after the standard
     *             {@code username, password, firstName, lastName, id}:
     *             <ul>
     *                 <li>student → {@code int year, double gpa}</li>
     *                 <li>teacher → {@code String employeeId, String dept, TeacherTitle title}</li>
     *                 <li>manager → {@code String employeeId, String dept, ManagerType type}</li>
     *                 <li>admin → {@code String employeeId, String dept}</li>
     *             </ul>
     */
    public static User createUser(String type,
                                  String username, String password,
                                  String firstName, String lastName, String id,
                                  Object... args) {
        switch (type.toLowerCase()) {
            case "student": {
                int year = (int) args[0];
                double gpa = (double) args[1];
                return new Student(username, password, firstName, lastName, id, year, gpa);
            }
            case "teacher": {
                String empId = (String) args[0];
                String dept = (String) args[1];
                TeacherTitle title = (TeacherTitle) args[2];
                return new Teacher(username, password, firstName, lastName, id, empId, dept, title);
            }
            case "manager": {
                String empId = (String) args[0];
                String dept = (String) args[1];
                ManagerType mt = (ManagerType) args[2];
                return new Manager(username, password, firstName, lastName, id, empId, dept, mt);
            }
            case "admin": {
                String empId = (String) args[0];
                String dept = (String) args[1];
                return new Admin(username, password, firstName, lastName, id, empId, dept);
            }
            default:
                throw new IllegalArgumentException("Unknown user type: " + type);
        }
    }
}
