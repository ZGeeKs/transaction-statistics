package com.github.zgeeks.tx.rest;

import com.github.zgeeks.tx.TransactionService;
import com.github.zgeeks.tx.domain.ImmutableTransaction;
import com.github.zgeeks.tx.domain.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transactions")
    public ResponseEntity<Void> transactions(@RequestBody TransactionDto transaction) {
        Status status = transactionService.createTransaction(ImmutableTransaction.builder()
                .timestamp(transaction.getTimestamp()).cost(transaction.getCost()).build());
        switch (status) {
            case REJECTED: return ResponseEntity.noContent().build();
            case ACCEPTED: return ResponseEntity.status(HttpStatus.CREATED).build();
            case INVALID: throw new RuntimeException();
            default:
                throw new RuntimeException();
        }
    }
}
