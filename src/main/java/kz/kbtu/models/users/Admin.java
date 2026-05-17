package kz.kbtu.models.users;

import kz.kbtu.patterns.Database;
import kz.kbtu.patterns.EventManager;

import java.io.Serializable;
import java.util.List;

/**
 * System admin: can add/remove users and view action logs.
 */
public class Admin extends Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    public Admin() {}

    public Admin(String username, String password, String firstName, String lastName, String id,
                 String employeeId, String department) {
        super(username, password, firstName, lastName, id, employeeId, department);
    }

    public void addUser(User user) {
        Database.getInstance().getUsers().add(user);
        EventManager.getInstance().notify("Admin " + fullName() + " added user " + user.fullName());
    }

    public void removeUser(User user) {
        Database.getInstance().getUsers().remove(user);
        EventManager.getInstance().notify("Admin " + fullName() + " removed user " + user.fullName());
    }

    public List<String> viewLogs() {
        return EventManager.getInstance().getLog();
    }

    @Override
    public String toString() {
        return "Admin{" + fullName() + ", dept=" + department + "}";
    }
}
