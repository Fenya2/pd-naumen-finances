package com.finances.service.category;

public class CategoryNotFoundException extends CategoryServiceException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
