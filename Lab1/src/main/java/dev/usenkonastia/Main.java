package dev.usenkonastia;

import dev.usenkonastia.model.ChthonicCreature;
import dev.usenkonastia.tasks.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Main class to execute the program for generating, processing, and analyzing
 * ChthonicCreature objects.
 * <p>
 * This class is the entry point of the application, invoking methods
 * to generate creatures, group them by type, and analyze their attack power statistics.
 * </p>
 *
 * @author Anastasiia Usenko
 */
public class Main {

    public static void main(String[] args) {
        Stream<ChthonicCreature> creatureStream = Task3.get();

        List<ChthonicCreature> gatheredCreatures = Task4.get(creatureStream);

        Map<String, List<ChthonicCreature>> groupedCreatures = Task5.filterAndGroupByType(gatheredCreatures, 100, 500);

        groupedCreatures.forEach((type, creatures) -> {
            System.out.println("Вид істоти: " + type);
            creatures.forEach(System.out::println);
        });

       Task6.getStatistics(gatheredCreatures);

       Task7.analyzeAttackPowers(gatheredCreatures);
    }
}