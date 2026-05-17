package kz.kbtu.api.sets;

import kz.kbtu.api.Lesson;
import kz.kbtu.api.role.Teacher;

import java.util.stream.Stream;

public interface Teachers extends Users<Teacher> {
    Stream<Teacher> byLesson(Lesson lesson);
}
