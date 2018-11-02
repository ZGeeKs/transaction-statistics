package com.github.zgeeks.tx.statistics.bucket;

import com.github.zgeeks.tx.statistics.domain.ImmutableTransaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.util.DoubleSummaryStatistics;

import static org.junit.Assert.*;

public class SummaryBucketTest {

    private SummaryBucket summaryBucket;

    @Before
    public void setUp() {
        summaryBucket = new SummaryBucket();
    }

    @Test
    public void test_create_new_summary_statistics() {
        summaryBucket.accept(ImmutableTransaction.builder().timestamp(Clock.systemUTC().millis()).cost(100).build());
        Assert.assertEquals(1, summaryBucket.toDoubleSummaryStatistics().getCount());
        Assert.assertEquals(100.0, summaryBucket.toDoubleSummaryStatistics().getSum(), 0);
        Assert.assertEquals(100.0, summaryBucket.toDoubleSummaryStatistics().getMax(), 0);
        Assert.assertEquals(100.0, summaryBucket.toDoubleSummaryStatistics().getMin(), 0);
        Assert.assertEquals(100.0, summaryBucket.toDoubleSummaryStatistics().getAverage(), 0);
        summaryBucket.accept(ImmutableTransaction.builder().timestamp(Clock.systemUTC().millis()).cost(300).build());
        Assert.assertEquals(2, summaryBucket.toDoubleSummaryStatistics().getCount());
        Assert.assertEquals(400.0, summaryBucket.toDoubleSummaryStatistics().getSum(), 0);
        Assert.assertEquals(300.0, summaryBucket.toDoubleSummaryStatistics().getMax(), 0);
        Assert.assertEquals(100.0, summaryBucket.toDoubleSummaryStatistics().getMin(), 0);
        Assert.assertEquals(200.0, summaryBucket.toDoubleSummaryStatistics().getAverage(), 0);
    }

    @Test
    public void test_reset_summary_statistics() {
        summaryBucket.accept(ImmutableTransaction.builder().timestamp(Clock.systemUTC().millis()).cost(100).build());
        Assert.assertEquals(1, summaryBucket.toDoubleSummaryStatistics().getCount());
        Assert.assertEquals(100.0, summaryBucket.toDoubleSummaryStatistics().getSum(), 0);
        Assert.assertEquals(100.0, summaryBucket.toDoubleSummaryStatistics().getMax(), 0);
        Assert.assertEquals(100.0, summaryBucket.toDoubleSummaryStatistics().getMin(), 0);
        Assert.assertEquals(100.0, summaryBucket.toDoubleSummaryStatistics().getAverage(), 0);
        summaryBucket.accept(ImmutableTransaction.builder().timestamp(Clock.systemUTC().millis() - 60_000).cost(300).build());
        Assert.assertEquals(1, summaryBucket.toDoubleSummaryStatistics().getCount());
        Assert.assertEquals(300.0, summaryBucket.toDoubleSummaryStatistics().getSum(), 0);
        Assert.assertEquals(300.0, summaryBucket.toDoubleSummaryStatistics().getMax(), 0);
        Assert.assertEquals(300.0, summaryBucket.toDoubleSummaryStatistics().getMin(), 0);
        Assert.assertEquals(300.0, summaryBucket.toDoubleSummaryStatistics().getAverage(), 0);
    }

    @Test
    public void isPartOfLastMinute() {
    }

    @Test
    public void toDoubleSummaryStatistics() {
    }
}