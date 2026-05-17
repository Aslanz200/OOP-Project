package kz.kbtu.models.academic;

import java.io.Serializable;

public class Lesson implements Serializable {
    private static final long serialVersionUID = 1L;

    private LessonType type;
    private Course course;

    public Lesson() {}

    public Lesson(LessonType type, Course course) {
        this.type = type;
        this.course = course;
    }

    public LessonType getType() { return type; }
    public void setType(LessonType type) { this.type = type; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    @Override
    public String toString() {
        return "Lesson{" + type + " of " + (course != null ? course.getCourseId() : "?") + "}";
    }
}
