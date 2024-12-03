package com.finances.dto.goal;

import com.finances.model.Goal;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GoalMapper {
    public static GoalGetResponse toGoalGetResponse(Goal goal) {
        return new GoalGetResponse(goal.getId(),
                goal.getOwner().getId(),
                goal.getName(),
                goal.getAmount(),
                goal.getDate(),
                goal.getAccount().getId());
    }

    public static GoalsForUserGetResponse toGoalsForUserGetResponse(long[] array) {
        return new GoalsForUserGetResponse(array);
    }

    public static GoalCreateResponse toGoalCreateResponse(Goal goal) {
        return new GoalCreateResponse(goal.getId());
    }
}
