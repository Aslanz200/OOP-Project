package kz.kbtu.api;

import java.util.stream.Stream;

public interface Semester {

    int year();
    String part();
    Stream<Mark> marks();

}
