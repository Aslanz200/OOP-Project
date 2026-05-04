package kz.kbtu.entity.role;

import kz.kbtu.entity.Course;
import kz.kbtu.entity.Role;

import java.util.List;

public interface Student extends Role {

    int year();
    float gpa();

    int fails();
    int credits();

    List<Course> courses();
}
