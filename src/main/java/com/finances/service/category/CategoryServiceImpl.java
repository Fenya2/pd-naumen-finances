package com.finances.service.category;


import com.finances.model.User;
import com.finances.model.Category;
import com.finances.repository.CategoryRepository;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.finances.model.Category.CategoryType;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Category create(String name, User owner, @Nonnull Category parentCategory) {
        checkCategoryExistsForCreate(name, owner, parentCategory);

        Category newCategory = new Category(name, owner, parentCategory);
        categoryRepository.save(newCategory);

        return newCategory;
    }

    @Override
    public Category getDefaultIncomeCategoryForUser(User user) {
        Category defaultIncomeCategory =
                categoryRepository.getByOwnerAndDefaultCategoryTrueAndType(user, CategoryType.INCOME);

        if (defaultIncomeCategory == null) {
            throw new DefaultCategoryNotFoundException("Default income category not found");
        }

        return defaultIncomeCategory;
    }

    @Override
    public Category getDefaultExpenseCategoryForUser(User user) {
        Category defaultIncomeCategory =
                categoryRepository.getByOwnerAndDefaultCategoryTrueAndType(user, CategoryType.EXPENSE);

        if (defaultIncomeCategory == null) {
            throw new DefaultCategoryNotFoundException("Default expense category not found");
        }

        return defaultIncomeCategory;
    }

    @Override
    public Category findById(long id) {
        return categoryRepository.findById(id)
                .orElseThrow(
                        () -> new CategoryNotFoundException("Category with id " + id + " not found")
                );
    }

    @Override
    public Category findByIdSilent(long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void createDefaultCategoriesForUser(User user) {
        checkDefaultsCategoryExists(user);

        Category newDefaultExpenseCategory = new Category("defaultExpense", user, null);
        newDefaultExpenseCategory.setType(Category.CategoryType.EXPENSE);
        newDefaultExpenseCategory.setDefaultCategory(true);

        Category newDefaultIncomeCategory = new Category("defaultIncome", user, null);
        newDefaultIncomeCategory.setType(Category.CategoryType.INCOME);
        newDefaultIncomeCategory.setDefaultCategory(true);

        categoryRepository.save(newDefaultExpenseCategory);
        categoryRepository.save(newDefaultIncomeCategory);
    }

    private void checkCategoryExistsForCreate(String name, User owner, Category parentCategory) {
        Optional<Category> category = categoryRepository.getByNameAndOwnerAndParent(name, owner, parentCategory);
        if (category.isPresent()) {
            throw new CategoryAlreadyExistException("Category with name " + name + " already exists");
        }
    }

    private void checkDefaultsCategoryExists(User owner) {
        List<Category> allDefaultCategoryByOwner = categoryRepository.getAllCategoryByOwnerAndDefaultCategoryTrue(owner);
        if (!allDefaultCategoryByOwner.isEmpty()) {
            throw new DefaultCategoryExistException("Default category for " + owner.getName() + " already exists");
        }
    }
}
