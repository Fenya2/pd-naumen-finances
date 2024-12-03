package com.finances.service.goal;

public class GoalAlreadyExistException extends GoalServiceException {
    public GoalAlreadyExistException(String message) {
        super(message);
    }
}
