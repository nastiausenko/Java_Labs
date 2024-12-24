package dev.usenkonastia.utils;

import dev.usenkonastia.model.ChthonicCreature;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Gatherer;

public class CreatureGatherer implements Gatherer<ChthonicCreature, List<ChthonicCreature>, ChthonicCreature> {
    private final String skipType;
    private  final int skipCount;
    private final int limit;

    public CreatureGatherer(String skipType, int skipCount, int limit) {
        this.skipType = skipType;
        this.skipCount = skipCount;
        this.limit = limit;
    }

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

    @Override
    public Supplier<List<ChthonicCreature>> initializer() {
        return ArrayList::new;
    }


    @Override
    public BiConsumer<List<ChthonicCreature>, Downstream<? super ChthonicCreature>> finisher() {
        return (state, downstream) -> {
            for (ChthonicCreature creature : state) {
                downstream.push(creature);
            }
        };
    }
}
