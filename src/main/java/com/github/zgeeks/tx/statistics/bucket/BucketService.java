package com.github.zgeeks.tx.statistics.bucket;

import com.github.zgeeks.tx.statistics.domain.Statistics;
import com.github.zgeeks.tx.statistics.domain.Transaction;

public interface BucketService {
    enum Status {
        ACCEPTED, INVALID, REJECTED
    }

    Status createTransaction(Transaction transaction);
    Statistics getLastStatistics();
}
