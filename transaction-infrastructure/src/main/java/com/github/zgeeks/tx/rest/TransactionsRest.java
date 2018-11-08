package com.github.zgeeks.tx.rest;

import com.github.zgeeks.tx.rest.model.TransactionDTO;

import javax.ws.rs.core.Response;

public class TransactionsRest implements TranactionsApi {

    @Override
    public Response createTransaction(TransactionDTO body) {
        return null;
    }

    @Override
    public Response getStatistics() {
        return null;
    }
}
