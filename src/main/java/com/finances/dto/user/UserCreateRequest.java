package com.finances.dto.user;

public record UserCreateRequest(String login, String password, String name) {
}
