package com.github.zgeeks.tx.statistics.bucket;

import com.github.zgeeks.tx.statistics.domain.Transaction;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Block {

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
    private Body body;
    private long lastUpdatedTimestamp;

    public Block() {}

    public void update(Transaction transaction) {
        readWriteLock.writeLock().lock();
        try {
            if (lastUpdatedTimestamp < transaction.timestamp()) {
                replace(transaction);
            } else {
                create(transaction);
            }
            lastUpdatedTimestamp = transaction.timestamp();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    private void create(Transaction transaction) {
        body = ImmutableBody.builder()
            .count(body.count() + 1)
            .max(Math.max(transaction.cost(), body.max()))
            .min(Math.min(transaction.cost(), body.min()))
            .sum(body.sum() + transaction.cost())
            .build();
    }

    private void replace(Transaction transaction) {
        body = ImmutableBody.builder()
            .count(1)
            .max(transaction.cost())
            .min(transaction.cost())
            .sum(transaction.cost())
            .build();
    }

    boolean isPartOfLastMinute(long timestamp) {
        readWriteLock.writeLock().lock();
        try {
            return timestamp - lastUpdatedTimestamp <= 60_000;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }
}
