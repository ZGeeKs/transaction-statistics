package com.github.zgeeks.tx.monitoring;

public interface MonitorService {
    void takeMeasurement(long timestamp, double cost);
    Statistics getStatistics();
}
