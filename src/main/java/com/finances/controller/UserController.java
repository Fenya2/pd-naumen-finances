package com.finances.controller;

import com.finances.dto.user.UserCreateRequest;
import com.finances.dto.user.UserCreateResponse;
import com.finances.dto.user.UserMapper;
import com.finances.model.User;
import com.finances.service.user.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "create")
    public UserCreateResponse createUser(@RequestBody UserCreateRequest userCreateRequest) {
        User user = userService.create(
                userCreateRequest.login(),
                userCreateRequest.password(),
                userCreateRequest.name()
        );
        return UserMapper.toUserCreateResponse(user);
    }
}
