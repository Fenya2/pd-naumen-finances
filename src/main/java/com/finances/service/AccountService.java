package com.finances.service;

import com.finances.model.User;

/**
 * Сервис для работы со счетами
 */
public interface AccountService {
    /**
     * Создает счет по умолчанию для пользователя. Если он уже существует, ничего не делает
     */
    void createDefaultAccountForUser(User user);
}
