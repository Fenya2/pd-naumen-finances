package com.finances.dto.goal;

import java.util.Date;

public record GoalCreateRequest(long userId, String name, double amount, Date date) {}
