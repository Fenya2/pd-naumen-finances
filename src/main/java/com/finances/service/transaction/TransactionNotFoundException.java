package com.finances.service.transaction;

public class TransactionNotFoundException extends TransactionServiceException {
    public TransactionNotFoundException(String message) {
        super(message);
    }
}
