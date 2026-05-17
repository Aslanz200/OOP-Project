package kz.kbtu.impl.database.sql;

import kz.kbtu.api.Course;
import kz.kbtu.api.Lesson;

import java.time.LocalTime;
import java.util.UUID;

public class LessonImpl implements Lesson {

    private final UUID id;
    private final Course course;
    private final int week;
    private final LocalTime start;
    private final LocalTime end;
    private final String extra;

    public LessonImpl(UUID id, Course course, int week, LocalTime start, LocalTime end, String extra) {
        this.id = id;
        this.course = course;
        this.week = week;
        this.start = start;
        this.end = end;
        this.extra = extra;
    }

    @Override
    public UUID id() {
        return id;
    }

    @Override
    public Course course() {
        return course;
    }

    @Override
    public String extra() {
        return extra;
    }

    @Override
    public int week() {
        return week;
    }

    @Override
    public LocalTime start() {
        return start;
    }

    @Override
    public LocalTime end() {
        return end;
    }
}
