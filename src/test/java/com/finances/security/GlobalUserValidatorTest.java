package com.finances.security;

import com.finances.model.User;
import com.finances.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class GlobalUserValidatorTest {

    @Mock
    private UserService userService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private GlobalUserValidator globalUserValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsValidUserIsAdminReturnsTrue() {
        final User adminUser = new User();
        adminUser.setUserRole(UserRole.ADMIN);
        adminUser.setLogin("admin");

        final long userId = 1L;

        when(userService.findById(userId)).thenReturn(adminUser);

        final boolean result = globalUserValidator.isValid(authentication, userId);

        assertTrue(result, "Administrator should always be valid");
    }

    @Test
    void testIsValidUserIsNotAdminAndLoginMatchesReturnsTrue() {
        final User normalUser = new User();
        normalUser.setUserRole(UserRole.USER);
        normalUser.setLogin("john");

        final long userId = 1L;

        when(userService.findById(userId)).thenReturn(normalUser);
        when(authentication.getName()).thenReturn("john");

        final boolean result = globalUserValidator.isValid(authentication, userId);

        assertTrue(result, "User should be valid if login matches");
    }

    @Test
    void testIsValidUserIsNotAdminAndLoginDoesNotMatchReturnsFalse() {
        final User normalUser = new User();
        normalUser.setUserRole(UserRole.USER);
        normalUser.setLogin("john_doe");

        final long userId = 1L;

        when(userService.findById(userId)).thenReturn(normalUser);
        when(authentication.getName()).thenReturn("jane_doe");

        final boolean result = globalUserValidator.isValid(authentication, userId);

        assertFalse(result, "User should not be valid if login does not match");
    }
}
