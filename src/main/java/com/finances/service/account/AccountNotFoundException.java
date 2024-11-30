package com.finances.service.account;

public class AccountNotFoundException extends AccountServiceException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
