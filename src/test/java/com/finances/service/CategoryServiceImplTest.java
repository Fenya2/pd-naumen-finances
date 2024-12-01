package com.finances.service;

import com.finances.model.Category;
import com.finances.model.User;
import com.finances.repository.CategoryRepository;
import com.finances.service.category.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User();
        testUser.setId(1L);
        testUser.setName("Test User");
    }

    @Test
    void create_ShouldCreateCategory_WhenValidInput() {
        // Given
        Category parentCategory = new Category("Parent", testUser, null);
        parentCategory.setId(1L);

        when(categoryRepository.getByNameAndOwnerAndParent("Child", testUser, parentCategory))
                .thenReturn(Optional.empty());

        // When
        Category createdCategory = categoryService.create("Child", testUser, parentCategory);

        // Then
        verify(categoryRepository, times(1)).save(any(Category.class));
        assertThat(createdCategory).isNotNull();
        assertThat(createdCategory.getName()).isEqualTo("Child");
        assertThat(createdCategory.getOwner()).isEqualTo(testUser);
        assertThat(createdCategory.getParent()).isEqualTo(parentCategory);
    }

    @Test
    void create_ShouldThrowException_WhenCategoryExists() {
        Category parentCategory = new Category("Parent", testUser, null);
        parentCategory.setId(1L);

        when(categoryRepository.getByNameAndOwnerAndParent("Child", testUser, parentCategory))
                .thenReturn(Optional.of(new Category()));

        assertThatThrownBy(() -> categoryService.create("Child", testUser, parentCategory))
                .isInstanceOf(CategoryAlreadyExistException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    void getDefaultIncomeCategoryForUser_ShouldReturnCategory_WhenDefaultExists() {
        Category defaultIncomeCategory = new Category("Default Income", testUser, null);
        defaultIncomeCategory.setDefaultCategory(true);

        when(categoryRepository.getByOwnerAndDefaultCategoryTrue(testUser))
                .thenReturn(defaultIncomeCategory);

        Category result = categoryService.getDefaultCategoryForUser(testUser);

        assertThat(result).isEqualTo(defaultIncomeCategory);
    }

    @Test
    void getDefaultIncomeCategoryForUser_ShouldThrowException_WhenDefaultNotFound() {
        when(categoryRepository.getByOwnerAndDefaultCategoryTrue(testUser))
                .thenReturn(null);

        assertThatThrownBy(() -> categoryService.getDefaultCategoryForUser(testUser))
                .isInstanceOf(DefaultCategoryNotFoundException.class)
                .hasMessageContaining("Default income category not found");
    }

    @Test
    void createDefaultsCategoryForUser_ShouldCreateDefaultCategories_WhenNoneExist() {
        when(categoryRepository.getCategoryByOwnerAndDefaultCategoryTrue(testUser))
                .thenReturn(null);

        categoryService.createDefaultCategoriesForUser(testUser);

        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void createDefaultsCategoryForUser_ShouldThrowException_WhenDefaultsExist() {
        Category existingDefault = new Category("Default", testUser, null);
        when(categoryRepository.getCategoryByOwnerAndDefaultCategoryTrue(testUser))
                .thenReturn(existingDefault);

        assertThatThrownBy(() -> categoryService.createDefaultCategoriesForUser(testUser))
                .isInstanceOf(DefaultCategoryExistException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    void findById_ShouldReturnCategory_WhenFound() {
        Category category = new Category("Category", testUser, null);
        category.setId(1L);

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Category result = categoryService.findById(1L);

        assertThat(result).isEqualTo(category);
    }

    @Test
    void findById_ShouldThrowException_WhenNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> categoryService.findById(1L))
                .isInstanceOf(CategoryNotFoundException.class)
                .hasMessageContaining("not found");
    }
}
