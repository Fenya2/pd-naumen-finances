package com.finances.service.category;

public class CategoryAlreadyExistException extends CategoryServiceException {
    public CategoryAlreadyExistException(String message) {
        super(message);
    }
}
