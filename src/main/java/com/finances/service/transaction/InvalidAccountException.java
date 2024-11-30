package com.finances.service.transaction;

public class InvalidAccountException extends TransactionServiceException {
    public InvalidAccountException(String message) {
        super(message);
    }
}
