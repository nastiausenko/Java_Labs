package dev.usenkonastia.tasks;

import dev.usenkonastia.model.ChthonicCreature;
import dev.usenkonastia.stats.AttackPowerStatisticsCollector;

import java.util.List;

public class Task6 {
    public static void getStatistics(List<ChthonicCreature> creatures) {
        String attackPowerStats = creatures.stream().collect(new AttackPowerStatisticsCollector()).toString();

        System.out.println("\nСтатистика сили атаки:");
        System.out.println(attackPowerStats);
    }
}