package com.github.zgeeks.tx.statistics.bucket;

import org.immutables.value.Value;

@Value.Immutable
public interface Body {
    Body EMPTY = ImmutableBody.builder().count(1).max(0.0).min(0.0).sum(0.0).build();
    double sum();
    double max();
    double min();
    long count();
}

