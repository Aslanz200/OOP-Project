package university;

public class Admin extends Employee {

    public Admin() {
        super();
    }

    public Admin(String username, String password, String firstName, String lastName, String id,
                 String employeeID, String department) {
        super(username, password, firstName, lastName, id, employeeID, department);
    }

    public void addUser(User user) {
        // TODO: implement adding a user to the system database
    }

    public void removeUser(User user) {
        // TODO: implement removing a user from the system database
    }

    @Override
    public String toString() {
        return "Admin{" + super.toString() + '}';
    }
}
