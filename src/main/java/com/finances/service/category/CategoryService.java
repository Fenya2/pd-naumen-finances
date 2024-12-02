package com.finances.service.category;

import com.finances.model.Goal;
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
     * Возвращает дефолтную категорию для пользователя
     *
     * @param user пользователь, владелец категории
     * @return дефолтная категория пользователя или или {@link DefaultCategoryNotFoundException}
     */
    Category getDefaultCategoryForUser(User user);

    /**
     * Создает категории по умолчанию для пользователя
     */
    void createDefaultCategoriesForUser(User user);

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

    Category getGoalCategory(Goal goal);
}
