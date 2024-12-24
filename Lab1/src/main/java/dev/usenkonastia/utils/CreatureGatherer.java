package dev.usenkonastia.utils;

import dev.usenkonastia.model.ChthonicCreature;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Gatherer;

/**
 * A custom {@link Gatherer} implementation for collecting instances of {@link ChthonicCreature}.
 * This gatherer allows for skipping a specified number of creatures of a given type and enforcing a limit
 * on the total number of creatures collected. The collect process considers the type of each creature,
 * and creatures of a specified type are skipped until the given limit is reached.
 *
 * @author Anastasiia Usenko
 */
public class CreatureGatherer implements Gatherer<ChthonicCreature, List<ChthonicCreature>, ChthonicCreature> {
    private final String skipType;
    private  final int skipCount;
    private final int limit;

    /**
     * Constructs a new {@link CreatureGatherer} with the specified criteria for skipping and limiting.
     *
     * @param skipType the type of creature to skip
     * @param skipCount the number of creatures of the specified type to skip
     * @param limit the total number of creatures to collect
     */
    public CreatureGatherer(String skipType, int skipCount, int limit) {
        this.skipType = skipType;
        this.skipCount = skipCount;
        this.limit = limit;
    }

    /**
     * Handles the accumulation of {@link ChthonicCreature} objects into a list,
     * skipping the specified number of creatures of the given type and respecting the collection limit.
     *
     * @return an {@link Integrator} for accumulating {@link ChthonicCreature} objects
     */
    @Override
    public Integrator<List<ChthonicCreature>, ChthonicCreature, ChthonicCreature> integrator() {
        return new Integrator<>() {
            private int skipped = 0;

            @Override
            public boolean integrate(List<ChthonicCreature> state, ChthonicCreature element, Downstream<? super ChthonicCreature> downstream) {
                if (state.size() >= limit) {
                    return false;
                }

                if (element.type().equals(skipType) && skipped < skipCount) {
                    skipped++;
                    return true;
                }

                state.add(element);
                return true;
            }
        };
    }

    /**
     * Provides a new, empty {@link List} to accumulate {@link ChthonicCreature} objects.
     *
     * @return a supplier that provides a new {@link ArrayList} for accumulating creatures
     */
    @Override
    public Supplier<List<ChthonicCreature>> initializer() {
        return ArrayList::new;
    }

    /**
     * Processes the final list of collected creatures, pushing each creature to the downstream.
     *
     * @return a {@link BiConsumer} for finishing the collection process
     */
    @Override
    public BiConsumer<List<ChthonicCreature>, Downstream<? super ChthonicCreature>> finisher() {
        return (state, downstream) -> {
            for (ChthonicCreature creature : state) {
                downstream.push(creature);
            }
        };
    }
}
