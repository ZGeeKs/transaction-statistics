package com.github.zgeeks.tx.service.statistics;

import com.github.zgeeks.tx.monitoring.Summary;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;

public class SummaryBucketTest {

    private Summary summaryBucket;

    @BeforeEach
    public void setUp() {
        summaryBucket = new Summary();
    }

    @Test
    public void test_create_new_summary_statistics() {
        summaryBucket.accept(Clock.systemUTC().millis(), 100);
        Assertions.assertEquals(1, summaryBucket.toDoubleSummaryStatistics().getCount());
        Assertions.assertEquals(100.0, summaryBucket.toDoubleSummaryStatistics().getSum());
        Assertions.assertEquals(100.0, summaryBucket.toDoubleSummaryStatistics().getMax());
        Assertions.assertEquals(100.0, summaryBucket.toDoubleSummaryStatistics().getMin());
        Assertions.assertEquals(100.0, summaryBucket.toDoubleSummaryStatistics().getAverage());
        summaryBucket.accept(Clock.systemUTC().millis() + 1_000, 300);
        Assertions.assertEquals(2, summaryBucket.toDoubleSummaryStatistics().getCount());
        Assertions.assertEquals(400.0, summaryBucket.toDoubleSummaryStatistics().getSum());
        Assertions.assertEquals(300.0, summaryBucket.toDoubleSummaryStatistics().getMax());
        Assertions.assertEquals(100.0, summaryBucket.toDoubleSummaryStatistics().getMin());
        Assertions.assertEquals(200.0, summaryBucket.toDoubleSummaryStatistics().getAverage());
    }

    @Test
    public void test_reset_summary_statistics() {
        summaryBucket.accept(Clock.systemUTC().millis(), 100);
        Assertions.assertEquals(1, summaryBucket.toDoubleSummaryStatistics().getCount());
        Assertions.assertEquals(100.0, summaryBucket.toDoubleSummaryStatistics().getSum());
        Assertions.assertEquals(100.0, summaryBucket.toDoubleSummaryStatistics().getMax());
        Assertions.assertEquals(100.0, summaryBucket.toDoubleSummaryStatistics().getMin());
        Assertions.assertEquals(100.0, summaryBucket.toDoubleSummaryStatistics().getAverage());
        summaryBucket.accept(Clock.systemUTC().millis() - 60_000, 300);
        Assertions.assertEquals(1, summaryBucket.toDoubleSummaryStatistics().getCount());
        Assertions.assertEquals(300.0, summaryBucket.toDoubleSummaryStatistics().getSum());
        Assertions.assertEquals(300.0, summaryBucket.toDoubleSummaryStatistics().getMax());
        Assertions.assertEquals(300.0, summaryBucket.toDoubleSummaryStatistics().getMin());
        Assertions.assertEquals(300.0, summaryBucket.toDoubleSummaryStatistics().getAverage());
    }

    @Test
    public void isPartOfLastMinute() {
    }

    @Test
    public void toDoubleSummaryStatistics() {
    }
}