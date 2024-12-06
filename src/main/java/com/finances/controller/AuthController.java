package com.finances.controller;

import com.finances.dto.user.UserCreateRequest;
import com.finances.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Контролллер с эндоинтами на авторизацию и регистрацию
 */
@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLogin() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String getRegistration() {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String processRegistration(UserCreateRequest userCreateRequest) {
        userService.create(userCreateRequest.login(),
                userCreateRequest.password(),
                userCreateRequest.name());
        return "redirect:/login";
    }
}