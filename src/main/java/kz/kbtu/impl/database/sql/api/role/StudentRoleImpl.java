package kz.kbtu.impl.database.sql.api.role;

import kz.kbtu.api.Course;
import kz.kbtu.api.Lesson;
import kz.kbtu.api.Mark;
import kz.kbtu.api.Role;
import kz.kbtu.api.role.Student;
import kz.kbtu.impl.database.sql.SQLBasedSchool;

import java.util.stream.Stream;

public class StudentRoleImpl implements Student {

    private final SQLBasedSchool database;
    private final StudentUserImpl user;

    public StudentRoleImpl(SQLBasedSchool database, StudentUserImpl user) {
        this.database = database;
        this.user = user;
    }

    @Override
    public int year() {
        return 0;
    }

    @Override
    public float gpa() {

        return 0;
    }

    @Override
    public int fails() {



        return 0;
    }

    @Override
    public int credits() {
        return 0;
    }

    @Override
    public Stream<Mark> marks() {
        return Stream.empty();
    }

    @Override
    public Stream<Course> courses() {
        return Stream.empty();
    }

    @Override
    public void dropCourse(Course course) {

    }

    @Override
    public void enrollCourse(Course course) {

    }

    @Override
    public Stream<Lesson> lessons() {
        return Stream.empty();
    }

    @Override
    public Class<? extends Role> implementation() {
        return Student.class;
    }
}
