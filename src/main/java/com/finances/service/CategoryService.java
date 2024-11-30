package com.finances.service;

import com.finances.model.User;

public interface CategoryService
{
    /**
     * Создает категории по умолчанию для пользователя.
     */
    void createDefaultCategoriesForUser(User user);
}
