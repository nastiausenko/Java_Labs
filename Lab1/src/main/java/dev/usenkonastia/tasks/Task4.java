package dev.usenkonastia.tasks;

import dev.usenkonastia.model.ChthonicCreature;
import dev.usenkonastia.utils.CreatureGatherer;

import java.util.List;
import java.util.stream.Stream;

/**
 * Task 4: Filters and collects creatures based on specific criteria.
 *
 * @author  Anastasiia Usenko
 * @see     CreatureGatherer
 */
public class Task4 {

    public static List<ChthonicCreature> get(Stream<ChthonicCreature> creatures) {
        return creatures.gather(new CreatureGatherer("Перевертень", 100, 500)).toList();
    }
}