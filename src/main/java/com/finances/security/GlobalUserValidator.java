package com.finances.security;

import com.finances.model.User;
import com.finances.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Objects;

/**
 * Сервис валидации пользователей, делающих запрос
 */
@Service
public class GlobalUserValidator {
    private final UserService userService;

    @Autowired
    public GlobalUserValidator(UserService userService) {
        this.userService = userService;
    }

    public boolean isValid(Principal principal, long userId) {
        final User user = userService.findById(userId);
        return Objects.equals(user.getLogin(), principal.getName());
    }
}
