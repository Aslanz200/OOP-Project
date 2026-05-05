package kz.kbtu.impl.entity.role;

import kz.kbtu.entity.Database;
import kz.kbtu.entity.User;
import kz.kbtu.entity.role.Student;

public class StudentBuilderImpl implements Student.StudentBuilder {

    private final Database database;
    private int year;
    private float gpa;
    private int fails;
    private int credits;

    public StudentBuilderImpl(Database database) {
        this.database = database;
    }

    @Override
    public Class<Student> implementation() {
        return Student.class;
    }

    @Override
    public Student build(User<Student> user) {
        return new StudentImpl(
                database,
                user,
                gpa, fails, credits, year
        );
    }

    @Override
    public Student.StudentBuilder year(int yr) {
        this.year = yr;
        return this;
    }

    @Override
    public Student.StudentBuilder gpa(float gpa) {
        this.gpa = gpa;
        return this;
    }

    @Override
    public Student.StudentBuilder fails(int fails) {
        this.fails = fails;
        return this;
    }

    @Override
    public Student.StudentBuilder credits(int credits) {
        this.credits = credits;
        return this;
    }
}
