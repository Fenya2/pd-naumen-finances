package com.finances.service.goal;

public class GoalNotFoundException extends GoalServiceException {
    public GoalNotFoundException(String message) {
        super(message);
    }
}
