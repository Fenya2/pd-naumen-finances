package com.finances.service;

public class DefaultCategoryNotFoundException extends CategoryServiceException {
    public DefaultCategoryNotFoundException(String message) {
        super(message);
    }
}
