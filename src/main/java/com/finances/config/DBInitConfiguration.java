package com.finances.config;

import com.finances.security.UserRole;
import com.finances.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Заполняет базу данных перед стартом приложения необходимыми данными
 */
@Component
public class DBInitConfiguration {

    private final UserService userService;

    @Autowired
    public DBInitConfiguration(UserService userService) {
        this.userService = userService;
    }

    /**
     * Создает в БД администратора
     */
    @EventListener(ApplicationReadyEvent.class)
    public void createDefaultUsers() {
        userService.createAdmin("admin", "admin", "admin");
        userService.create("user", "user", "user");
    }
}
