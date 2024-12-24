package dev.usenkonastia.tasks;

import dev.usenkonastia.model.AttackPowerSummary;
import dev.usenkonastia.model.ChthonicCreature;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Task 7: Analyzes attack power statistics and identifies outliers using the IQR method.
 *
 * @author  Anastasiia Usenko
 * @see     AttackPowerSummary
 */
public class Task7 {

    public static void analyzeAttackPowers(List<ChthonicCreature> creatures) {
        List<Integer> attackPowers = extractAttackPowers(creatures);

        double q1 = getPercentile(attackPowers, 25);
        double q3 = getPercentile(attackPowers, 75);
        double iqr = calculateIQR(q1, q3);

        double lowerBound = calculateLowerBound(q1, iqr);
        double upperBound = calculateUpperBound(q3, iqr);

        Map<String, List<ChthonicCreature>> groupedCreatures = groupByOutliers(creatures, lowerBound, upperBound);

        AttackPowerSummary result = calculateResult(groupedCreatures);

        printStatistics(iqr, lowerBound, upperBound, result);
    }

    private static List<Integer> extractAttackPowers(List<ChthonicCreature> creatures) {
        return creatures.stream()
                .map(ChthonicCreature::attackPower)
                .sorted()
                .collect(Collectors.toList());
    }

    private static double getPercentile(List<Integer> sortedList, double percentile) {
        int index = (int) Math.ceil(percentile / 100.0 * sortedList.size()) - 1;
        return sortedList.get(Math.max(index, 0));
    }

    private static double calculateIQR(double q1, double q3) {
        return q3 - q1;
    }

    private static double calculateLowerBound(double q1, double iqr) {
        return q1 - 1.5 * iqr;
    }

    private static double calculateUpperBound(double q3, double iqr) {
        return q3 + 1.5 * iqr;
    }

    private static Map<String, List<ChthonicCreature>> groupByOutliers(List<ChthonicCreature> creatures, double lowerBound, double upperBound) {
        return creatures.stream()
                .collect(Collectors.groupingBy(creature -> {
                    int attackPower = creature.attackPower();
                    return (attackPower < lowerBound || attackPower > upperBound) ? "outliers" : "data";
                }));
    }

    private static AttackPowerSummary calculateResult(Map<String, List<ChthonicCreature>> groupedCreatures) {
        int data = groupedCreatures.getOrDefault("data", Collections.emptyList()).size();
        int outliers = groupedCreatures.getOrDefault("outliers", Collections.emptyList()).size();
        return new AttackPowerSummary(data, outliers);
    }

    private static void printStatistics(double iqr, double lowerBound, double upperBound, AttackPowerSummary summary) {
        System.out.println("Міжквартильний розмах (IQR): " + iqr);
        System.out.println("Межі викидів: [" + lowerBound + ", " + upperBound + "]");
        System.out.println("Результат: " + summary);
    }
}
