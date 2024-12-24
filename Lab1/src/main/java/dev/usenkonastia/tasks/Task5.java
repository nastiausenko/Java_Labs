package dev.usenkonastia.tasks;

import dev.usenkonastia.model.ChthonicCreature;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Task 5: Filters and groups chthonic creatures by their type based on the type and years since their first mention.
 *
 * @author Anastasiia Usenko
 */
public class Task5 {

    public static Map<String, List<ChthonicCreature>> filterAndGroupByType(List<ChthonicCreature> creatures, int minYears, int maxYears) {
        LocalDate currentDate = LocalDate.now();

        return creatures.stream()
                .filter(creature -> {
                    long yearsSinceFirstMention = ChronoUnit.YEARS.between(creature.firstMentionDate(), currentDate);
                    return yearsSinceFirstMention >= minYears && yearsSinceFirstMention <= maxYears;
                })
                .collect(Collectors.groupingBy(ChthonicCreature::type));
    }
}