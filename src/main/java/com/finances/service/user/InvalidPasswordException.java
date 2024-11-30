package com.finances.service.user;

public class InvalidPasswordException extends UserServiceException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
