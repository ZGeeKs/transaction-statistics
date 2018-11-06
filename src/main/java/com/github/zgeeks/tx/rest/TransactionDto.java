package com.github.zgeeks.tx.rest;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionDto {
    private final long timestamp;
    private final double cost;

    @JsonCreator
    public TransactionDto(@JsonProperty("timestamp") long timestamp,
                          @JsonProperty("cost") double cost) {
        this.timestamp = timestamp;
        this.cost = cost;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public double getCost() {
        return cost;
    }
}
