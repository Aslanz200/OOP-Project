package university;

public class Manager extends Employee {
    private ManagerType managerType;

    public Manager() {
        super();
    }

    public Manager(String username, String password, String firstName, String lastName, String id,
                   String employeeID, String department, ManagerType managerType) {
        super(username, password, firstName, lastName, id, employeeID, department);
        this.managerType = managerType;
    }

    public ManagerType getManagerType() {
        return managerType;
    }

    public void setManagerType(ManagerType managerType) {
        this.managerType = managerType;
    }

    public void approveRegistration(Student student) {
        // TODO: implement registration approval logic
    }

    public void assignTeacher(Teacher teacher, Course course) {
        // TODO: implement teacher-to-course assignment logic
    }

    public void manageNews() {
        // TODO: implement news creation, editing, and removal
    }

    @Override
    public String toString() {
        return "Manager{" +
                "managerType=" + managerType +
                ", " + super.toString() +
                '}';
    }
}
