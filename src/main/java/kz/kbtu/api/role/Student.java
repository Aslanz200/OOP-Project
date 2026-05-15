package kz.kbtu.api.role;

import kz.kbtu.api.Mark;
import kz.kbtu.api.Role;

import java.util.stream.Stream;

public interface Student extends Role {

    int year();
    float gpa();

    int fails();
    int credits();

    Stream<Mark> marks();

    interface StudentBuilder extends Role.RoleBuilder<Student> {
        StudentBuilder year(int yr);
        StudentBuilder gpa(float gpa);
        StudentBuilder fails(int fails);
        StudentBuilder credits(int credits);
    }
}
