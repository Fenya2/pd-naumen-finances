package com.finances.security;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Роли пользователей в приложении
 */
@Getter
public enum UserRole {
    USER(new SimpleGrantedAuthority("ROLE_USER")),
    ADMIN(new SimpleGrantedAuthority("ROLE_ADMIN"));

    private final SimpleGrantedAuthority springRole;

    UserRole(final SimpleGrantedAuthority springRole) {
        this.springRole = springRole;
    }
}
