package com.finances.controller;

import com.finances.dto.goal.*;
import com.finances.model.Goal;
import com.finances.model.User;
import com.finances.service.category.CategoryAlreadyExistException;
import com.finances.service.category.DefaultCategoryNotFoundException;
import com.finances.service.goal.GoalAlreadyExistException;
import com.finances.service.goal.GoalNotFoundException;
import com.finances.service.goal.GoalService;
import com.finances.service.user.UserNotFoundException;
import com.finances.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/goal")
public class GoalController {
    private final GoalService goalService;
    private final UserService userService;

    @Autowired
    public GoalController(GoalService goalService, UserService userService) {
        this.goalService = goalService;
        this.userService = userService;
    }

    @GetMapping(value = "/id")
    @ResponseStatus(HttpStatus.OK)
    public GoalGetResponse getGoal(@RequestParam long idGoal) {
        Goal goal = goalService.findById(idGoal);
        return GoalMapper.toGoalGetResponse(goal);
    }

    @GetMapping(value = "/user")
    @ResponseStatus(HttpStatus.OK)
    public GoalsForUserGetResponse getGoalsForUser(@RequestParam long idUser) {
        User user = userService.findById(idUser);

        List<Goal> goalsForUser = goalService.getAllGoalsForUser(user);

        return GoalMapper.toGoalsForUserGetResponse(goalsForUser.stream()
                .mapToLong(Goal::getId)
                .toArray());
    }

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public GoalCreateResponse createGoal(@RequestBody GoalCreateRequest goalCreateRequest) {
        User user = userService.findById(goalCreateRequest.userId());
        Goal goal = goalService.createGoal(user,
                goalCreateRequest.name(),
                goalCreateRequest.amount(),
                goalCreateRequest.date());

        return GoalMapper.toGoalCreateResponse(goal);
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Exception exceptionUserNotFound(Exception e) {
        Exception exception = new Exception(e.getMessage());
        exception.setStackTrace(Arrays.stream(e.getStackTrace()).limit(5).toArray(StackTraceElement[]::new));
        return exception;
    }

    @ExceptionHandler(GoalNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Exception exceptionGoalNotFound(Exception e) {
        Exception exception = new Exception(e.getMessage());
        exception.setStackTrace(Arrays.stream(e.getStackTrace()).limit(5).toArray(StackTraceElement[]::new));
        return exception;
    }

    @ExceptionHandler(DefaultCategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Exception exceptionDefaultCategoryNotFound(Exception e) {
        Exception exception = new Exception(e.getMessage());
        exception.setStackTrace(Arrays.stream(e.getStackTrace()).limit(5).toArray(StackTraceElement[]::new));
        return exception;
    }

    @ExceptionHandler(GoalAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Exception exceptionGoalAlreadyExist(Exception e) {
        Exception exception = new Exception(e.getMessage());
        exception.setStackTrace(Arrays.stream(e.getStackTrace()).limit(5).toArray(StackTraceElement[]::new));
        return exception;
    }

    @ExceptionHandler(CategoryAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Exception exceptionCategoryAlreadyExist(Exception e) {
        Exception exception = new Exception(e.getMessage());
        exception.setStackTrace(Arrays.stream(e.getStackTrace()).limit(5).toArray(StackTraceElement[]::new));
        return exception;
    }

    @ExceptionHandler(CategoryAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Exception exceptionIllegalArgumentException(Exception e) {
        Exception exception = new Exception(e.getMessage());
        exception.setStackTrace(Arrays.stream(e.getStackTrace()).limit(5).toArray(StackTraceElement[]::new));
        return exception;
    }
}

