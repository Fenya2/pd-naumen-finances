package com.finances.dto.goal;

import java.util.Date;

public record GoalGetResponse(long id,
                          long idOwner,
                          String name,
                          double amount,
                          Date date,
                          long idAccount) {}