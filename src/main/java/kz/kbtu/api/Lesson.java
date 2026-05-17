package kz.kbtu.api;

import java.time.LocalTime;
import java.util.UUID;

public interface Lesson {
    UUID id();
    Course course();
    String extra();

    int week();
    LocalTime start();
    LocalTime end();
}
