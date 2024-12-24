package dev.usenkonastia.tasks;

import dev.usenkonastia.utils.ChthonicCreatureGenerator;
import dev.usenkonastia.model.ChthonicCreature;

import java.util.stream.Stream;

/**
 * Task 3: Generates a stream of chthonic creatures.
 *
 * @author Anastasiia Usenko
 * @see ChthonicCreatureGenerator
 */
public class Task3 {

    public static Stream<ChthonicCreature> get() {
        return ChthonicCreatureGenerator.generate();
    }
}
