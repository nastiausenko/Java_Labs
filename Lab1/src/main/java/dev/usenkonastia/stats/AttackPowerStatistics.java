package dev.usenkonastia.stats;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing statistics of attack powers for a group of ChthonicCreature objects.
 * <p>
 * Provides methods to calculate average, minimum, maximum, sum, and standard deviation
 * of attack power values. This class is mutable and allows for incremental updates.
 * </p>
 *
 * @author Anastasiia Usenko
 */
public class AttackPowerStatistics {
    private final List<Integer> attackPowers = new ArrayList<>();

    /**
     * Adds an attack power value to the statistics.
     *
     * @param income The attack power value to add.
     */
    public void add(int income) {
        attackPowers.add(income);
    }


    /**
     * Merges another AttackPowerStatistics object into this one.
     *
     * @param stats The other AttackPowerStatistics object to merge.
     */
    public void addAll(AttackPowerStatistics stats) {
        attackPowers.addAll(stats.attackPowers);
    }

    /**
     * Calculates the average attack power.
     *
     * @return The average attack power as a double.
     */
    public double getAvg() {
        return (double) getSum() / attackPowers.size();
    }

    /**
     * Retrieves the minimum attack power value.
     *
     * @return The minimum attack power value, or 0 if no values are present.
     */
    private Integer getMin() {
        return attackPowers.stream()
                .min(Integer::compareTo)
                .orElse(0);
    }

    /**
     * Retrieves the maximum attack power value.
     *
     * @return The maximum attack power value, or 0 if no values are present.
     */
    private Integer getMax() {
        return attackPowers.stream()
                .max(Integer::compareTo)
                .orElse(0);
    }

    /**
     * Calculates the sum of all attack power values.
     *
     * @return The sum of all attack power values.
     */
    private Integer getSum() {
        return attackPowers.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    /**
     * Calculates the standard deviation of attack power values.
     *
     * @return The standard deviation of attack power values as a double.
     */
    private double getDeviation() {
        double dispersion = calcDispersion();
        return Math.sqrt(dispersion);
    }

    /**
     * Calculates the dispersion of attack power values.
     *
     * @return The dispersion of attack power values as a double.
     */
    private double calcDispersion() {
        double avg = getAvg();
        double sum = 0;
        for (int income : attackPowers) {
            sum += Math.pow(income - avg, 2);
        }
        return sum / attackPowers.size();
    }

    /**
     * Returns a string representation of the attack power statistics.
     *
     * @return A string containing min, max, average, and standard deviation values.
     */
    @Override
    public String toString() {
        return "AttackPowerStatistics{" +
               "min=" + getMin() +
               ", max=" + getMax() +
               ", average=" + getAvg() +
               ", standardDeviation=" + getDeviation() +
               '}';
    }
}
