package dev.usenkonastia;

import java.util.List;
import java.util.stream.Stream;

public class Task4 {
    public static List<ChthonicCreature> get(Stream<ChthonicCreature> creatures) {
        return creatures.gather(new CreatureGatherer("Перевертень", 100, 500)).toList();
    }
}