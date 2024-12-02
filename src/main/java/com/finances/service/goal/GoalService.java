package com.finances.service.goal;

import com.finances.model.Goal;
import com.finances.model.User;

import java.util.Date;

public interface GoalService {
    Goal createGoal(User user, String name, double amount, Date date);
    void depositFromUserToGoal(User user, Goal goal, Date date, double amount);
    void withdrawFromGoalToUser(Goal goal, User user, Date date, double amount);
    void depositFromGoalToGoal(Goal goalFrom, Goal goalTo, Date date, double amount);
    Goal findById(long id);
}
