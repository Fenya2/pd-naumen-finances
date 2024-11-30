package com.finances.service;

public class CategoryNotFoundException extends CategoryServiceException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
