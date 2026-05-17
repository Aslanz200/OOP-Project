package kz.kbtu.models.academic;

import kz.kbtu.models.users.Student;

import java.io.Serializable;

/**
 * A student's mark in a specific course. Composed of two attestations (30% each) and a final exam (40%).
 */
public class Mark implements Serializable {
    private static final long serialVersionUID = 1L;

    private Student student;
    private Course course;
    private double attestation1;
    private double attestation2;
    private double finalExam;

    public Mark() {}

    public Mark(Student student, Course course,
                double attestation1, double attestation2, double finalExam) {
        this.student = student;
        this.course = course;
        this.attestation1 = attestation1;
        this.attestation2 = attestation2;
        this.finalExam = finalExam;
    }

    /** Weighted total: 30% + 30% + 40%. */
    public double getTotal() {
        return attestation1 * 0.3 + attestation2 * 0.3 + finalExam * 0.4;
    }

    public String getLetterGrade() {
        double t = getTotal();
        if (t >= 90) return "A";
        if (t >= 75) return "B";
        if (t >= 60) return "C";
        if (t >= 50) return "D";
        return "F";
    }

    public boolean isFail() { return getTotal() < 50; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public double getAttestation1() { return attestation1; }
    public void setAttestation1(double v) { this.attestation1 = clamp(v); }

    public double getAttestation2() { return attestation2; }
    public void setAttestation2(double v) { this.attestation2 = clamp(v); }

    public double getFinalExam() { return finalExam; }
    public void setFinalExam(double v) { this.finalExam = clamp(v); }

    private static double clamp(double v) { return Math.max(0, Math.min(100, v)); }

    @Override
    public String toString() {
        return "Mark{" + (student != null ? student.fullName() : "?") +
                " @ " + (course != null ? course.getCourseId() : "?") +
                " total=" + String.format("%.1f", getTotal()) + " (" + getLetterGrade() + ")}";
    }
}
