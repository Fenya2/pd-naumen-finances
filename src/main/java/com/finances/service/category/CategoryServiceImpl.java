package com.finances.service.category;


import com.finances.dto.category.view.CategoryNode;
import com.finances.model.Goal;
import com.finances.model.User;
import com.finances.model.Category;
import com.finances.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Category create(String name, User owner, Category parentCategory) {
        checkCategoryExistsForCreate(name, owner, parentCategory);

        Category newCategory = new Category(name, owner, parentCategory);
        categoryRepository.save(newCategory);

        return newCategory;
    }

    @Override
    public Category save(Category category) {
        checkCorrectCategory(category);

        return categoryRepository.save(category);
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
    public List<Category> getAllCategoriesForUser(User user) {
        return categoryRepository.findAllByOwner(user);
    }

    @Override
    public List<CategoryNode> buildCategoryTree(List<Category> categories) {
        Map<Long, CategoryNode> categoryNodeMap = new HashMap<>();
        List<CategoryNode> roots = new ArrayList<>();

        for (Category category : categories) {
            categoryNodeMap.put(category.getId(), new CategoryNode(category.getId(), category.getName()));
        }

        for (Category category : categories) {
            Long parentId = category.getParent() != null ? category.getParent().getId() : null;
            CategoryNode currentNode = categoryNodeMap.get(category.getId());

            if (parentId == null) {
                roots.add(currentNode);
            } else {
                CategoryNode parentNode = categoryNodeMap.get(parentId);
                if (parentNode != null) {
                    parentNode.getChildren().add(currentNode);
                }
            }
        }

        return roots;
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

    @Override
    public List<Category> getChildrenCategoriesForCategory(Category category) {
        return categoryRepository.getByOwnerAndParent(category.getOwner(), category);
    }

    private void checkCorrectCategory(Category category) {
        Optional<Category> optionalCategory = categoryRepository.findById(category.getId());
        if (optionalCategory.isPresent()) {
            throw new CategoryAlreadyExistException("Category with id " + category.getId() + " already exists");
        }

        Optional<Category> optionalCategory1 = categoryRepository.getByNameAndOwnerAndParent(category.getName(),
                category.getOwner(), category.getParent());
        if (optionalCategory1.isPresent()) {
            throw new CategoryAlreadyExistException("Category with name " + category.getName() + " already exists");
        }
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
