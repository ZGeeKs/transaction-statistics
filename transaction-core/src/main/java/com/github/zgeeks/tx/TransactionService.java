package com.github.zgeeks.tx;

import com.github.zgeeks.tx.domain.Status;
import com.github.zgeeks.tx.domain.Transaction;

public interface TransactionService {
    Status createTransaction(Transaction transaction);
}
