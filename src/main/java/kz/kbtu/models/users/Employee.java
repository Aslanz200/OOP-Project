package kz.kbtu.models.users;

import java.io.Serializable;

/**
 * Abstract base for any employee of the university (teachers, managers, admins).
 */
public abstract class Employee extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String employeeId;
    protected String department;

    public Employee() {}

    public Employee(String username, String password, String firstName, String lastName, String id,
                    String employeeId, String department) {
        super(username, password, firstName, lastName, id);
        this.employeeId = employeeId;
        this.department = department;
    }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
}
