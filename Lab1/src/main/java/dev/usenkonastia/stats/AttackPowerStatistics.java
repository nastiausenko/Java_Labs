package dev.usenkonastia.stats;

import java.util.ArrayList;
import java.util.List;

public class AttackPowerStatistics {
    private final List<Integer> attackPowers = new ArrayList<>();

    public void add(int income) {
        attackPowers.add(income);
    }

    public void addAll(AttackPowerStatistics stats) {
        attackPowers.addAll(stats.attackPowers);
    }

    public double getAvg() {
        return (double) getSum() / attackPowers.size();
    }

    private Integer getMin() {
        return attackPowers.stream()
                .min(Integer::compareTo)
                .orElse(0);
    }

    private Integer getMax() {
        return attackPowers.stream()
                .max(Integer::compareTo)
                .orElse(0);
    }

    private Integer getSum() {
        return attackPowers.stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    private double getDeviation() {
        double dispersion = calcDispersion();
        return Math.sqrt(dispersion);
    }

    private double calcDispersion() {
        double avg = getAvg();
        double sum = 0;
        for (int income : attackPowers) {
            sum += Math.pow(income - avg, 2);
        }
        return sum / attackPowers.size();
    }

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
