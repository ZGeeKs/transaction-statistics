package com.github.zgeeks.tx.rest;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StatisticsDto {
    private final double sum;
    private final double max;
    private final double min;
    private final double avg;
    private final long count;

    @JsonCreator
    public StatisticsDto(@JsonProperty("sum") double sum,
                         @JsonProperty("max") double max,
                         @JsonProperty("min") double min,
                         @JsonProperty("avg") double avg,
                         @JsonProperty("count") long count) {
        this.sum = sum;
        this.max = max;
        this.min = min;
        this.avg = avg;
        this.count = count;
    }

    public double getSum() {
        return sum;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }

    public double getAvg() {
        return avg;
    }

    public long getCount() {
        return count;
    }
}
