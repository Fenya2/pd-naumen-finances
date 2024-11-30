package com.finances.service.user;

public class UserAlreadyExistException extends UserServiceException {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
