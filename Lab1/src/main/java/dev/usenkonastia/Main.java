package dev.usenkonastia;

import java.util.List;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Stream<ChthonicCreature> creatureStream = Task3.get();

        List<ChthonicCreature> gatheredCreatures = Task4.get(creatureStream);
        gatheredCreatures.forEach(System.out::println);
    }
}