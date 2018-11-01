package com.github.zgeeks.tx.statistics.domain;

import org.immutables.value.Value;

@Value.Immutable
public interface Statistics {
    double sum();
    double max();
    double min();
    double avg();
    long count();
}
