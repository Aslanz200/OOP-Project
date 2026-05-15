package kz.kbtu;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Main {
    static void main() {


    }

    interface Abc<T> {}

    class Xyz implements Abc<Integer> {}
    class Obv implements Abc<Double> {}
}
