package com.github.zgeeks.tx.service.statistics;

import com.github.zgeeks.tx.domain.Statistics;
import com.github.zgeeks.tx.service.MonitorService;

import java.time.Clock;
import java.time.OffsetTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.Instant.ofEpochMilli;
import static java.time.OffsetTime.ofInstant;
import static java.time.ZoneOffset.UTC;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;

public class LastMinuteMonitorStorage implements MonitorService {

    private static final long SIZE = 60_000;
    private final Clock clock;
    private final List<SummaryBucket> buckets;

    public LastMinuteMonitorStorage(Clock clock) {
        this.clock = clock;
        this.buckets = Stream
                .generate(SummaryBucket::new)
                .limit(SIZE)
                .collect(Collectors.toList());
    }

    @Override
    public void takeMeasurement(long timestamp, double cost) {
        int bucketIndex = milliSecondOfMinute(timestamp);
        buckets.get(bucketIndex).accept(timestamp, cost);
    }

    private static int milliSecondOfMinute(long epochMilliSecond) {
        OffsetTime epochTime = ofInstant(ofEpochMilli(epochMilliSecond), UTC);
        int milli = epochTime.getNano() / 1_000_000;
        return epochTime.get(SECOND_OF_MINUTE) * 1_000 + milli;
    }

    @Override
    public Statistics getStatistics() {
        long currentTimestamp = clock.millis();
        return buckets.stream()
            .filter(bucket -> bucket.isPartOf(currentTimestamp, SIZE))
            .map(SummaryBucket::toDoubleSummaryStatistics)
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
