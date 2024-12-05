package com.finances.security;

import com.finances.model.User;
import com.finances.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
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

    /**
     * Если пользователь администратор, возвращает true. Если пользователь не администратор, проверяет, что id
     * пользователя, выполняющего запрос, совпадает с переданным.
     */
    public boolean isValid(Authentication authentication, long userId) {
        final User user = userService.findById(userId);
        if(UserRole.ADMIN.equals(user.getUserRole())) {
            return true;
        }
        return Objects.equals(user.getLogin(), authentication.getName());
    }

    /**
     * Если пользователь, выполняющий запрос не администратор и его id не совпадает с текущим, выбрасывает
     * {@link AccessDeniedException}
     */
    public void checkUserIsValid(Authentication authentication, long userId) throws AccessDeniedException {
        if(!isValid(authentication, userId))
            throw new AccessDeniedException("Access denied");
    }
}
