package com.finances.repository;

import com.finances.model.Category;
import com.finances.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;

    private User testUser;
    private Category parentCategory;

    @BeforeEach
    void setUp() {
        // Создаем пользователя
        testUser = new User();
        testUser.setName("testuser");

        userRepository.save(testUser);

        // Создаем родительскую категорию
        parentCategory = new Category();
        parentCategory.setName("Parent Category");
        parentCategory.setOwner(testUser);
        parentCategory.setDefaultCategory(true);

        categoryRepository.save(parentCategory);

        // Создаем подкатегории
        Category childCategory1 = new Category();
        childCategory1.setName("Child Category 1");
        childCategory1.setOwner(testUser);
        childCategory1.setParent(parentCategory);
        childCategory1.setDefaultCategory(false);

        Category childCategory2 = new Category();
        childCategory2.setName("Child Category 2");
        childCategory2.setOwner(testUser);
        childCategory2.setParent(parentCategory);
        childCategory2.setDefaultCategory(false);

        categoryRepository.save(childCategory1);
        categoryRepository.save(childCategory2);
    }

    @Test
    void testGetByNameAndOwnerAndParent() {
        Optional<Category> category = categoryRepository.getByNameAndOwnerAndParent(
                "Child Category 1", testUser, parentCategory
        );

        assertThat(category).isPresent();
        assertThat(category.get().getName()).isEqualTo("Child Category 1");
    }

    @Test
    void testGetCategoryByOwnerAndDefaultCategoryTrue() {
        Category category = categoryRepository.getByOwnerAndDefaultCategoryTrue(testUser);

        assertThat(category.isDefaultCategory()).isTrue();
    }

    @Test
    void testGetByOwnerAndDefaultCategoryTrueAndType() {
        Category category = categoryRepository.getByOwnerAndDefaultCategoryTrue(testUser);

        assertThat(category).isNotNull();
        assertThat(category.getName()).isEqualTo("Parent Category");
        assertThat(category.isDefaultCategory()).isTrue();
    }
}
