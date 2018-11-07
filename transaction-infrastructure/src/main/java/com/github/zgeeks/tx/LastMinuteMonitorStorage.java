package com.github.zgeeks.tx;

import com.github.zgeeks.tx.monitoring.MonitorService;
import com.github.zgeeks.tx.monitoring.Statistics;
import com.github.zgeeks.tx.monitoring.Summary;
import com.github.zgeeks.tx.util.TimeUtils;

import java.time.Clock;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LastMinuteMonitorStorage implements MonitorService {

    private static final long SIZE = 60_000;
    private final Clock clock;
    private final List<Summary> buckets;

    public LastMinuteMonitorStorage(Clock clock) {
        this.clock = clock;
        this.buckets = Stream
                .generate(Summary::new)
                .limit(SIZE)
                .collect(Collectors.toList());
    }

    @Override
    public void takeMeasurement(long timestamp, double cost) {
        int bucketIndex = TimeUtils.milliSecondOfMinute(timestamp);
        buckets.get(bucketIndex).accept(timestamp, cost);
    }

    @Override
    public Statistics getStatistics() {
        long currentTimestamp = clock.millis();
        return buckets.stream()
            .filter(bucket -> bucket.isPartOf(currentTimestamp, SIZE))
            .map(Summary::toDoubleSummaryStatistics)
            .reduce(
                (a, b) -> {
                    a.combine(b);
                    return a;
                }
            )
            .map(Statistics.toStatistics())
            .orElse(Statistics.empty());
    }
}
