package com.finances.service.category;


import com.finances.model.Goal;
import com.finances.model.User;
import com.finances.model.Category;
import com.finances.repository.CategoryRepository;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Category getDefaultCategoryForUser(User user) {
        Category defaultIncomeCategory =
                categoryRepository.getByOwnerAndDefaultCategoryTrue(user);

        if (defaultIncomeCategory == null) {
            throw new DefaultCategoryNotFoundException("Default income category not found");
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
    public Category getGoalCategory(Goal goal) {
        User owner = goal.getOwner();
        Category defaultCategory = getDefaultCategoryForUser(owner);
        return categoryRepository.getByNameAndOwnerAndParent(goal.getName(), owner, defaultCategory).orElseThrow(
                () -> new CategoryNotFoundException("Category with name " + goal.getName() + " not found")
        );
    }

    @Override
    @Transactional
    public void createDefaultCategoriesForUser(User user) {
        checkDefaultsCategoryExists(user);

        Category newDefaultCategory = new Category("default", user, null);
        newDefaultCategory.setDefaultCategory(true);

        categoryRepository.save(newDefaultCategory);
    }

    private void checkCategoryExistsForCreate(String name, User owner, Category parentCategory) {
        Optional<Category> category = categoryRepository.getByNameAndOwnerAndParent(name, owner, parentCategory);
        if (category.isPresent()) {
            throw new CategoryAlreadyExistException("Category with name " + name + " already exists");
        }
    }

    private void checkDefaultsCategoryExists(User owner) {
        Category DefaultCategoryByOwner = categoryRepository.getByOwnerAndDefaultCategoryTrue(owner);
        if (DefaultCategoryByOwner != null) {
            throw new DefaultCategoryExistException("Default category for " + owner.getName() + " already exists");
        }
    }
}
