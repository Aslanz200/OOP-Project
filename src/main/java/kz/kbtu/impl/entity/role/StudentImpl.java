package kz.kbtu.impl.entity.role;

import kz.kbtu.entity.Database;
import kz.kbtu.entity.Role;
import kz.kbtu.entity.User;
import kz.kbtu.entity.role.Student;

import java.util.List;
import java.util.stream.Stream;

public class StudentImpl implements Student {
    private final Database database;

    private final User<Student> user;
    private final float gpa;
    private final int fails;
    private final int credits;
    private final int year;

    private final List<String> basePermissions = List.of(
            "view.schedule", "view.attestation", "view.student_finance"
    );

    public StudentImpl(Database database, User<Student> user, float gpa, int fails, int credits, int year) {
        this.database = database;
        this.user = user;
        this.gpa = gpa;
        this.fails = fails;
        this.credits = credits;
        this.year = year;
    }

    @Override
    public int year() {
        return this.year;
    }

    @Override
    public float gpa() {
        return this.gpa;
    }

    @Override
    public int fails() {
        return this.fails;
    }

    @Override
    public int credits() {
        return this.credits;
    }

    @Override
    public Stream<String> permissions() {
        return basePermissions.stream();
    }

    @Override
    public boolean can(String permission) {
        return basePermissions.contains(permission);
    }

    @Override
    public Class<? extends Role> implementation() {
        return Student.class;
    }
}
