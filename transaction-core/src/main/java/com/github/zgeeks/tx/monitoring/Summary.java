package com.github.zgeeks.tx.monitoring;

import static com.github.zgeeks.tx.util.LockUtils.runSafely;

import java.util.DoubleSummaryStatistics;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class Summary {

    private final ReadWriteLock readWriteLock;
    private DoubleSummaryStatistics doubleSummaryStatistics;
    private long lastUpdatedTimestamp;

    public Summary() {
        this.readWriteLock = new ReentrantReadWriteLock(true);
        this.doubleSummaryStatistics = new DoubleSummaryStatistics();
    }

    public void accept(long timestamp, double cost) {
        runSafely(readWriteLock.writeLock(), () -> {
            if (lastUpdatedTimestamp >= timestamp) {
                doubleSummaryStatistics = new DoubleSummaryStatistics();
            }
            doubleSummaryStatistics.accept(cost);
            lastUpdatedTimestamp = timestamp;
            return null;
        });
    }

    public boolean isPartOf(long timestamp, long duration) {
        return runSafely(readWriteLock.readLock(), () -> timestamp - lastUpdatedTimestamp <= duration);
    }

    public DoubleSummaryStatistics toDoubleSummaryStatistics() {
        return runSafely(readWriteLock.readLock(), () -> {
            DoubleSummaryStatistics ds = new DoubleSummaryStatistics();
            ds.combine(doubleSummaryStatistics);
            return ds;
        });
    }
}
