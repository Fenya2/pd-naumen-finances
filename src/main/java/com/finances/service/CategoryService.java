package com.finances.service;

import com.finances.model.User;
import com.finances.model.Category;

public interface CategoryService {
    /**
     * Создаёт категорию с именем {@param name} для пользователя {@param user}
     * для родительской категории {@param parentCategory}
     *
     * @param name - имя категории
     * @param user - пользователь, владеющий категорией
     * @param parentCategory - родительская категория для создаваемой категории
     * @return получившуюся категорию
     */
    Category create(String name, User user, Category parentCategory);

    /**
     * Возвращает дефолтную категорию доходов для пользователя
     *
     * @param user пользователь, владелец категории
     * @return дефолтная категория доходов пользователя или или {@link DefaultCategoryNotFoundException}
     */
    Category getDefaultIncomeCategoryForUser(User user);

    /**
     * Возвращает дефолтную категорию расходов для пользователя
     *
     * @param user пользователь, владелец категории
     * @return дефолтная категория расходов пользователя или {@link DefaultCategoryNotFoundException}
     */
    Category getDefaultExpenseCategoryForUser(User user);

    /**
     * Возвращает категорию по id категории
     *
     * @param id - идентификатор категории
     * @return искомая категория или {@link CategoryNotFoundException}
     */
    Category findById(long id);

    /**
     * Возвращает категорию по id категорию без исключений
     *
     * @param id - идентификатор категории
     * @return искомая категория или null
     */
    Category findByIdSilent(long id);

    /**
     * Создает категории по умолчанию для пользователя
     */
    void createDefaultCategoriesForUser(User user);
}
