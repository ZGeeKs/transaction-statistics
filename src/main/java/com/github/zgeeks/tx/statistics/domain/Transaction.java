package com.github.zgeeks.tx.statistics.domain;

import org.immutables.value.Value;

@Value.Immutable
public interface Transaction {
    long timestamp();
    double cost();
}
