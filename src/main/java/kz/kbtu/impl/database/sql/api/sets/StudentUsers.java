package kz.kbtu.impl.database.sql.api.sets;

import kz.kbtu.api.Course;
import kz.kbtu.api.Role;
import kz.kbtu.api.User;
import kz.kbtu.api.role.Student;
import kz.kbtu.api.sets.Students;
import kz.kbtu.api.sets.Users;
import kz.kbtu.impl.database.sql.SQLBasedSchool;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public class StudentUsers implements Students {

    private final SQLBasedSchool database;

    public StudentUsers(SQLBasedSchool database) {
        this.database = database;
    }

    @Override
    public Stream<User<Student>> byCourse(Course course) {
        return Stream.empty();
    }

    @Override
    public Stream<User<Student>> all() {
        return Stream.empty();
    }

    @Override
    public <Expect extends User<R>, R extends Role> Expect byRole(Class<R> roleClass, Class<Expect> expectedSet) {
        return null;
    }

    @Override
    public <R extends Role> Users<R> byRole(Class<Student> roleClass) {
        return null;
    }

    @Override
    public Users<Student> participants(Course course) {
        return null;
    }

    @Override
    public Optional<User<Student>> fetch(UUID id) {
        return Optional.empty();
    }

    @Override
    public Optional<User<Student>> fetch(String username) {
        return Optional.empty();
    }
}
