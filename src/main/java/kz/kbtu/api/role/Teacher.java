package kz.kbtu.api.role;

import kz.kbtu.api.Course;
import kz.kbtu.api.Lesson;
import kz.kbtu.api.Role;

import java.util.stream.Stream;

public interface Teacher extends Role {
    Stream<Course> instructs();
    Stream<Lesson> lessons();

    TeacherTitle title();

    interface TeacherBuilder extends Role.RoleBuilder<Teacher> {
        TeacherBuilder instructs(Course... courses);
        TeacherBuilder title(TeacherTitle title);
    }
}
