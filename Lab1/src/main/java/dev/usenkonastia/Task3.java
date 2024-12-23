package dev.usenkonastia;

import java.util.stream.Stream;

public class Task3 {
    public static Stream<ChthonicCreature> get() {
        return ChthonicCreatureGenerator.generate();
    }
}
