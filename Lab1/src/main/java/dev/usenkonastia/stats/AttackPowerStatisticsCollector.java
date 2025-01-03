package dev.usenkonastia.stats;

import dev.usenkonastia.model.ChthonicCreature;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * A custom {@link Collector} that accumulates statistics about the attack power of chthonic creatures.
 * Processes a stream of {@link ChthonicCreature} objects and calculates aggregated statistics
 * specifically related to their attack power. It accumulates attack power values into an {@link AttackPowerStatistics}
 * object, enabling efficient computation and representation of aggregated statistics.
 *
 * @author Anastasiia Usenko
 */
public class AttackPowerStatisticsCollector implements Collector<ChthonicCreature, AttackPowerStatistics, AttackPowerStatistics> {

    @Override
    public Supplier<AttackPowerStatistics> supplier() {
        return AttackPowerStatistics::new;
    }

    @Override
    public BiConsumer<AttackPowerStatistics, ChthonicCreature> accumulator() {
        return (statistics, creature) -> statistics.add(creature.attackPower());
    }

    @Override
    public BinaryOperator<AttackPowerStatistics> combiner() {
        return (stats1, stats2) -> {
            stats1.addAll(stats2);
            return stats1;
        };
    }

    @Override
    public Function<AttackPowerStatistics, AttackPowerStatistics> finisher() {
        return Function.identity();
        }

    @Override
    public Set<Collector.Characteristics> characteristics() {
        return Set.of();
    }
}
