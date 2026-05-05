package kz.kbtu.entity.role;

import kz.kbtu.entity.Course;
import kz.kbtu.entity.Role;

import java.util.Iterator;
import java.util.List;

public interface Student extends Role {

    int year();
    float gpa();

    int fails();
    int credits();

    interface StudentBuilder extends Role.RoleBuilder<Student> {
        StudentBuilder year(int yr);
        StudentBuilder gpa(float gpa);
        StudentBuilder fails(int fails);
        StudentBuilder credits(int credits);
    }
}
