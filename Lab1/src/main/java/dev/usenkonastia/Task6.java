package dev.usenkonastia;

import java.util.List;

public class Task6 {
    public static void getStatistics(List<ChthonicCreature> creatures) {
        String attackPowerStats = creatures.stream().collect(new AttackPowerStatisticsCollector()).toString();

        System.out.println("\nСтатистика сили атаки:");
        System.out.println(attackPowerStats);
    }
}
