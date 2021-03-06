package com.github.zgeeks.tx;

import com.github.zgeeks.tx.domain.Status;
import com.github.zgeeks.tx.domain.Transaction;
import com.github.zgeeks.tx.monitoring.MonitorService;

import java.time.Clock;
import java.util.concurrent.CompletableFuture;

public class MonitoringTransaction implements TransactionService {

    private static final long SIZE = 60_000;

    private final Clock clock;
    private final MonitorService monitorService;

    public MonitoringTransaction(Clock clock, MonitorService monitorService) {
        this.clock = clock;
        this.monitorService = monitorService;
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
        CompletableFuture.runAsync(() -> monitorService.takeMeasurement(transaction.timestamp(), transaction.cost()));
        return Status.ACCEPTED;
    }
}
