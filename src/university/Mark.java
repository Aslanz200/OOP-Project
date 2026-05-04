package university;

public class Mark {
    private Student student;
    private Course course;
    private double attestation1;
    private double attestation2;
    private double finalExam;

    public Mark() {
    }

    public Mark(Student student, Course course, double attestation1, double attestation2, double finalExam) {
        this.student = student;
        this.course = course;
        this.attestation1 = attestation1;
        this.attestation2 = attestation2;
        this.finalExam = finalExam;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public double getAttestation1() {
        return attestation1;
    }

    public void setAttestation1(double attestation1) {
        // TODO: validate score range (0-100)
        this.attestation1 = attestation1;
    }

    public double getAttestation2() {
        return attestation2;
    }

    public void setAttestation2(double attestation2) {
        // TODO: validate score range (0-100)
        this.attestation2 = attestation2;
    }

    public double getFinalExam() {
        return finalExam;
    }

    public void setFinalExam(double finalExam) {
        // TODO: validate score range (0-100)
        this.finalExam = finalExam;
    }

    @Override
    public String toString() {
        return "Mark{" +
                "student=" + (student != null ? student.getId() : "none") +
                ", course=" + (course != null ? course.getCourseID() : "none") +
                ", attestation1=" + attestation1 +
                ", attestation2=" + attestation2 +
                ", finalExam=" + finalExam +
                '}';
    }
}
