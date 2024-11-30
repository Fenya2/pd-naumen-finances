package com.finances.service.transaction;

public class InvalidAmountException extends TransactionServiceException {
    public InvalidAmountException(String message) {
        super(message);
    }
}
