package university;

public abstract class Employee extends User {
    private String employeeID;
    private String department;

    public Employee() {
        super();
    }

    public Employee(String username, String password, String firstName, String lastName, String id,
                    String employeeID, String department) {
        super(username, password, firstName, lastName, id);
        this.employeeID = employeeID;
        this.department = department;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeID='" + employeeID + '\'' +
                ", department='" + department + '\'' +
                ", " + super.toString() +
                '}';
    }
}
