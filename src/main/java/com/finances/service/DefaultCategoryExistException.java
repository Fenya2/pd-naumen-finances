package com.finances.service;

public class DefaultCategoryExistException extends CategoryServiceException {
    public DefaultCategoryExistException(String message) {
        super(message);
    }
}
