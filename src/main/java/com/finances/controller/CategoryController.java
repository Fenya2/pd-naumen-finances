package com.finances.controller;

import com.finances.dto.category.*;
import com.finances.model.Category;
import com.finances.model.User;
import com.finances.service.category.*;
import com.finances.service.user.UserNotFoundException;
import com.finances.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final UserService userService;

    @Autowired
    public CategoryController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping(value = "/root")
    @ResponseStatus(HttpStatus.OK)
    public CategoryGetResponse getDefaultCategory(@RequestParam int idUser) {
        User user = userService.findById(idUser);
        Category category = categoryService.getDefaultCategoryForUser(user);
        List<Category> childForCategory = categoryService.getChildrenCategoriesForCategory(category);
        return CategoryMapper.toCategoryGetResponse(category,
                childForCategory.stream()
                        .mapToLong(Category::getId)
                        .toArray());
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public CategoryGetResponse getCategory(@RequestParam int idCategory) {
        Category category = categoryService.findById(idCategory);
        List<Category> childForCategory = categoryService.getChildrenCategoriesForCategory(category);
        return CategoryMapper.toCategoryGetResponse(category,
                childForCategory.stream()
                        .mapToLong(Category::getId)
                        .toArray());
    }

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryCreateResponse createCategory(@RequestBody CategoryCreateRequest categoryCreateRequest) {
            User user = userService.findById(categoryCreateRequest.userId());
            Category category = categoryService.findById(categoryCreateRequest.parentId());
            Category newCategory = categoryService.create(categoryCreateRequest.name(), user, category);
            return CategoryMapper.toCategoryCreateResponse(newCategory);
    }

    @PutMapping(value = "/update")
    @ResponseStatus(HttpStatus.OK)
    public CategoryGetResponse updateCategoryName(@RequestBody CategoryUpdateRequest categoryUpdateRequest) {
        Category category = categoryService.findById(categoryUpdateRequest.categoryId());
        category.setName(categoryUpdateRequest.name());
        Category updatedCategory = categoryService.save(category);

        List<Category> childForCategory = categoryService.getChildrenCategoriesForCategory(updatedCategory);
        return CategoryMapper.toCategoryGetResponse(updatedCategory,
                childForCategory.stream()
                        .mapToLong(Category::getId)
                        .toArray());
    }


    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Exception exceptionUserNotFound(Exception e) {
        Exception exception = new Exception("User not found");
        exception.setStackTrace(Arrays.stream(e.getStackTrace()).limit(5).toArray(StackTraceElement[]::new));
        return exception;
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Exception exceptionCategoryNotFound(Exception e) {
        Exception exception = new Exception("Category not found");
        exception.setStackTrace(Arrays.stream(e.getStackTrace()).limit(5).toArray(StackTraceElement[]::new));
        return exception;
    }

    @ExceptionHandler(DefaultCategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Exception exceptionDefaultCategoryNotFound(Exception e) {
        Exception exception = new Exception("Default category not found");
        exception.setStackTrace(Arrays.stream(e.getStackTrace()).limit(5).toArray(StackTraceElement[]::new));
        return exception;
    }

    @ExceptionHandler(CategoryAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Exception exceptionCategoryAlreadyExist(Exception e) {
        Exception exception = new Exception("Category already exist");
        exception.setStackTrace(Arrays.stream(e.getStackTrace()).limit(5).toArray(StackTraceElement[]::new));
        return exception;
    }

    @ExceptionHandler(DefaultCategoryExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Exception exceptionDefaultCategoryAlreadyExist(Exception e) {
        Exception exception = new Exception("Default category already exist");
        exception.setStackTrace(Arrays.stream(e.getStackTrace()).limit(5).toArray(StackTraceElement[]::new));
        return exception;
    }
}
