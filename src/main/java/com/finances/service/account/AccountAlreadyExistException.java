package com.finances.service.account;

public class AccountAlreadyExistException extends AccountServiceException {
    public AccountAlreadyExistException(String message) {
        super(message);
    }
}
