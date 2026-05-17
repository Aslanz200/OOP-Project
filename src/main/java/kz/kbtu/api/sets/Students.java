package kz.kbtu.api.sets;

import kz.kbtu.api.Course;
import kz.kbtu.api.User;
import kz.kbtu.api.role.Student;

import java.util.stream.Stream;

public interface Students extends Users<Student> {
    Stream<User<Student>> byCourse(Course course);
}
