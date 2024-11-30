package com.finances.repository;

import com.finances.model.Category;
import com.finances.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private UserRepository userRepository;

    private User testUser;
    private Category parentCategoryExpense;
    private Category parentCategoryIncome;

    @BeforeEach
    void setUp() {
        // Создаем пользователя
        testUser = new User();
        testUser.setName("testuser");

        userRepository.save(testUser);

        // Создаем родительскую категорию
        parentCategoryExpense = new Category();
        parentCategoryExpense.setName("Parent Category Expense");
        parentCategoryExpense.setOwner(testUser);
        parentCategoryExpense.setType(Category.CategoryType.EXPENSE);
        parentCategoryExpense.setDefaultCategory(true);

        parentCategoryIncome = new Category();
        parentCategoryIncome.setName("Parent Category Income");
        parentCategoryIncome.setOwner(testUser);
        parentCategoryIncome.setType(Category.CategoryType.INCOME);
        parentCategoryIncome.setDefaultCategory(true);

        categoryRepository.save(parentCategoryExpense);
        categoryRepository.save(parentCategoryIncome);

        // Создаем подкатегории
        Category childCategory1 = new Category();
        childCategory1.setName("Child Category 1");
        childCategory1.setOwner(testUser);
        childCategory1.setParent(parentCategoryExpense);
        childCategory1.setType(Category.CategoryType.EXPENSE);
        childCategory1.setDefaultCategory(false);

        Category childCategory2 = new Category();
        childCategory2.setName("Child Category 2");
        childCategory2.setOwner(testUser);
        childCategory2.setParent(parentCategoryIncome);
        childCategory2.setType(Category.CategoryType.INCOME);
        childCategory2.setDefaultCategory(false);

        categoryRepository.save(childCategory1);
        categoryRepository.save(childCategory2);
    }

    @Test
    void testGetByNameAndOwnerAndParent() {
        Optional<Category> category = categoryRepository.getByNameAndOwnerAndParent(
                "Child Category 1", testUser, parentCategoryExpense
        );

        assertThat(category).isPresent();
        assertThat(category.get().getName()).isEqualTo("Child Category 1");
    }

    @Test
    void testGetAllCategoryByOwnerAndDefaultCategoryTrue() {
        List<Category> categories = categoryRepository.getAllCategoryByOwnerAndDefaultCategoryTrue(testUser);

        assertThat(categories).hasSize(2);
        assertThat(categories.get(0).isDefaultCategory()).isTrue();
        assertThat(categories.get(1).isDefaultCategory()).isTrue();
    }

    @Test
    void testGetByOwnerAndDefaultCategoryTrueAndType() {
        Category category = categoryRepository.getByOwnerAndDefaultCategoryTrueAndType(
                testUser, Category.CategoryType.INCOME
        );

        assertThat(category).isNotNull();
        assertThat(category.getName()).isEqualTo("Parent Category Income");
        assertThat(category.isDefaultCategory()).isTrue();
        assertThat(category.getType()).isEqualTo(Category.CategoryType.INCOME);
    }
}
