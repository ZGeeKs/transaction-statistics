package com.github.zgeeks.tx.domain;

import org.immutables.value.Value;

import java.util.DoubleSummaryStatistics;
import java.util.function.Function;

@Value.Immutable
public interface Statistics {
    double sum();
    double max();
    double min();
    double avg();
    long count();

    static Function<DoubleSummaryStatistics, Statistics> toStatistics(){
        return ds -> ImmutableStatistics.builder()
            .count(ds.getCount())
            .max(ds.getMax())
            .min(ds.getMin())
            .sum(ds.getSum())
            .avg(ds.getAverage())
            .build();
    }

    static Statistics empty(){
        return ImmutableStatistics.builder()
            .count(0).max(0).min(0).sum(0).avg(0)
            .build();
    }
}
