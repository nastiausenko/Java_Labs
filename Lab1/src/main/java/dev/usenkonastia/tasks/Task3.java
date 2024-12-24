package dev.usenkonastia.tasks;

import dev.usenkonastia.utils.ChthonicCreatureGenerator;
import dev.usenkonastia.model.ChthonicCreature;

import java.util.stream.Stream;

public class Task3 {
    public static Stream<ChthonicCreature> get() {
        return ChthonicCreatureGenerator.generate();
    }
}
