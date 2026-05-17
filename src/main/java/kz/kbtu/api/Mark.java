package kz.kbtu.api;

import java.util.Optional;

/**
 * Tied to a user
 */
public interface Mark {
    Course course();
    Semester semester();

    int absents();
    void absents(int count);

    Optional<Integer> firstHalf();
    void firstHalf(int mark);
    Optional<Integer> secondHalf();
    void secondHalf(int mark);
    Optional<Integer> finalExam();
    void finalExam(int mark);

}
