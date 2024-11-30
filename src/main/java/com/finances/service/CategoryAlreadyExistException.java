package com.finances.service;

public class CategoryAlreadyExistException extends CategoryServiceException {
    public CategoryAlreadyExistException(String message) {
        super(message);
    }
}
