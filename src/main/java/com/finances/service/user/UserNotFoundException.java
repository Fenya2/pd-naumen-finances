package com.finances.service.user;

public class UserNotFoundException extends UserServiceException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
