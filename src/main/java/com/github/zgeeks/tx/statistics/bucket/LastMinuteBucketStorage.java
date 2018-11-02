package com.github.zgeeks.tx.statistics.bucket;

import com.github.zgeeks.tx.statistics.domain.Statistics;
import com.github.zgeeks.tx.statistics.domain.Transaction;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LastMinuteBucketStorage implements BucketService {

    private static final long SIZE = 60_000;
    private final Clock clock;
    private final List<SummaryBucket> buckets;

    public LastMinuteBucketStorage(Clock clock) {
        this.clock = clock;
        this.buckets = Stream
                .generate(SummaryBucket::new)
                .limit(SIZE)
                .collect(Collectors.toList());
    }

    @Override
    public Status createTransaction(Transaction transaction) {
        long txTimestamp = transaction.timestamp();
        long millisInPast = clock.millis() - txTimestamp;
        if (millisInPast < 0) {
            return Status.INVALID;
        }
        if (millisInPast > SIZE) {
            return Status.REJECTED;
        }
        int bucketIndex = milliSecondOfMinute(txTimestamp);
        buckets.get(bucketIndex).accept(transaction);
        return Status.ACCEPTED;
    }

    private static int milliSecondOfMinute(long epochMilliSecond) {
        LocalDateTime epochSecond = LocalDateTime.ofEpochSecond(epochMilliSecond / 1_000, 0, ZoneOffset.UTC);
        int millis = (int)(epochMilliSecond % 1_000);
        return epochSecond.get(ChronoField.SECOND_OF_MINUTE) * 1_000 + millis;
    }

    @Override
    public Statistics getLastStatistics() {
        long currentTimestamp = clock.millis();
        return buckets.stream()
            .filter(bucket -> bucket.isPartOfLastMinute(currentTimestamp))
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
