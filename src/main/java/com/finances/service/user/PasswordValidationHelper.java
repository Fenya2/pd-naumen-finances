package com.finances.service.user;

/**
 * Класс, отвечающий за валидацию паролей
 */
public class PasswordValidationHelper {

    private PasswordValidationHelper() {
    }

    public static boolean isPasswordValid(final String password) {
        return password != null && !password.isEmpty();
    }
}
