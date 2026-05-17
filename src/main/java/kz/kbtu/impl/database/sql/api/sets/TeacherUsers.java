package kz.kbtu.impl.database.sql.api.sets;

import kz.kbtu.api.Course;
import kz.kbtu.api.Lesson;
import kz.kbtu.api.Role;
import kz.kbtu.api.User;
import kz.kbtu.api.role.Teacher;
import kz.kbtu.api.sets.Teachers;
import kz.kbtu.api.sets.Users;
import kz.kbtu.impl.database.sql.SQLBasedSchool;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public class TeacherUsers implements Teachers {

    private final SQLBasedSchool database;

    public TeacherUsers(SQLBasedSchool database) {
        this.database = database;
    }

    @Override
    public Stream<Teacher> byLesson(Lesson lesson) {
        return Stream.empty();
    }

    @Override
    public Stream<User<Teacher>> all() {
        return Stream.empty();
    }

    @Override
    public <Expect extends User<R>, R extends Role> Expect byRole(Class<R> roleClass, Class<Expect> expectedSet) {
        return null;
    }

    @Override
    public <R extends Role> Users<R> byRole(Class<Teacher> roleClass) {
        return null;
    }

    @Override
    public Users<Teacher> participants(Course course) {
        return null;
    }

    @Override
    public Optional<User<Teacher>> fetch(UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<User<Teacher>> fetch(String username) {
        return Optional.empty();
    }
}
