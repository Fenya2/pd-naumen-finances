package com.finances.dto.user;

import com.finances.model.User;

public class UserMapper {
    private UserMapper() {
    }

    public static UserCreateResponse toUserCreateResponse(User user) {
        return new UserCreateResponse(user.getId());
    }
}
