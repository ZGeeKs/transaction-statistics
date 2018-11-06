package com.github.zgeeks.tx;

import org.immutables.value.Value;

@Value.Immutable
public interface Transaction {
    long timestamp();
    double cost();
}
