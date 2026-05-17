package kz.kbtu.api.role;

import kz.kbtu.api.Course;
import kz.kbtu.api.Lesson;
import kz.kbtu.api.Mark;
import kz.kbtu.api.Role;

import java.util.stream.Stream;

public interface Student extends Role {

    int year();
    float gpa();

    int fails();
    int credits();

    Stream<Mark> marks();
    Stream<Course> courses();

    void dropCourse(Course course);
    void enrollCourse(Course course);

    Stream<Lesson> lessons();

    interface StudentBuilder extends Role.RoleBuilder<Student> {
        StudentBuilder year(int yr);
    }
}
