package dev.usenkonastia;

import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Stream<ChthonicCreature> creatureStream = Task3.get();
        creatureStream.limit(10).forEach(System.out::println);
    }
}