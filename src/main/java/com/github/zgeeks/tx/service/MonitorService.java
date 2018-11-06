package com.github.zgeeks.tx.service;

import com.github.zgeeks.tx.domain.Statistics;

public interface MonitorService {
    void takeMeasurement(long timestamp, double cost);
    Statistics getStatistics();
}
