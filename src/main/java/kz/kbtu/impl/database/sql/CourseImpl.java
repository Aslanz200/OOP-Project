package kz.kbtu.impl.database.sql;

import kz.kbtu.api.Course;

import java.util.UUID;

public class CourseImpl implements Course {

    private final UUID id;
    private final String codeName;
    private final String name;
    private final int hours;
    private final int credits;

    public CourseImpl(UUID id, String codeName, String name, int hours, int credits) {
        this.id = id;
        this.codeName = codeName;
        this.name = name;
        this.hours = hours;
        this.credits = credits;
    }

    public CourseImpl(String codeName, String name, int hours, int credits) {
        this(UUID.randomUUID(), codeName, name, hours, credits);
    }


    @Override
    public UUID id() {
        return id;
    }

    @Override
    public String codeName() {
        return codeName;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public int credits() {
        return credits;
    }

    @Override
    public int hours() {
        return hours;
    }
}
