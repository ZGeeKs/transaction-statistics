package com.github.zgeeks.tx.service.statistics;

import java.util.DoubleSummaryStatistics;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.github.zgeeks.tx.util.LockUtils.runSafely;

final class SummaryBucket {

    private final ReadWriteLock readWriteLock;
    private DoubleSummaryStatistics doubleSummaryStatistics;
    private long lastUpdatedTimestamp;

    SummaryBucket() {
        this.readWriteLock = new ReentrantReadWriteLock(true);
        this.doubleSummaryStatistics = new DoubleSummaryStatistics();
    }

    void accept(long timestamp, double cost) {
        runSafely(readWriteLock.writeLock(), () -> {
            if (lastUpdatedTimestamp >= timestamp) {
                doubleSummaryStatistics = new DoubleSummaryStatistics();
            }
            doubleSummaryStatistics.accept(cost);
            lastUpdatedTimestamp = timestamp;
            return null;
        });
    }

    boolean isPartOf(long timestamp, long duration) {
        return runSafely(readWriteLock.readLock(), () -> timestamp - lastUpdatedTimestamp <= duration);
    }

    DoubleSummaryStatistics toDoubleSummaryStatistics() {
        return runSafely(readWriteLock.readLock(), () -> {
            DoubleSummaryStatistics ds = new DoubleSummaryStatistics();
            ds.combine(doubleSummaryStatistics);
            return ds;
        });
    }
}
