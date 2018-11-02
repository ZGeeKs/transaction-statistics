package com.github.zgeeks.tx.statistics.bucket;

import com.github.zgeeks.tx.statistics.domain.ImmutableTransaction;
import com.github.zgeeks.tx.statistics.domain.Transaction;

import java.util.DoubleSummaryStatistics;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.github.zgeeks.tx.statistics.util.LockUtils.runSafely;

public class SummaryBucket {

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
    private DoubleSummaryStatistics doubleSummaryStatistics;
    private long lastUpdatedTimestamp;

    public SummaryBucket() {
        doubleSummaryStatistics = new DoubleSummaryStatistics();
    }

    public void accept(Transaction transaction) {
        runSafely(readWriteLock.writeLock(), () -> {
            if (lastUpdatedTimestamp >= transaction.timestamp()) {
                doubleSummaryStatistics = new DoubleSummaryStatistics();
            }
            doubleSummaryStatistics.accept(transaction.cost());
            lastUpdatedTimestamp = transaction.timestamp();
                return null;
            }
        );
    }

    boolean isPartOfLastMinute(long timestamp) {
        return runSafely(readWriteLock.readLock(), () -> timestamp - lastUpdatedTimestamp <= 60_000);
    }

    DoubleSummaryStatistics toDoubleSummaryStatistics() {
        return runSafely(readWriteLock.readLock(), () -> doubleSummaryStatistics);
    }
}
