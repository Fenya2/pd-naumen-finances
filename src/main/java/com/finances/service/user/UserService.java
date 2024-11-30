package com.finances.service.user;

import com.finances.model.User;

public interface UserService
{
    /**
     * Создает пользователя и все связанные с ним сущности, возвращает созданного пользователя
     */
    User create(String login, String password, String name);

    /**
     * Обновляет данные переданного пользователя (должен быть установлен id)
     */
    void update(User user);

    /**
     * Возвращает пользователя по его идентификатору
     */
    User findById(long id);
}
