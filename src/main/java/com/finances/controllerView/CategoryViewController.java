package com.finances.controllerView;

import com.finances.dto.category.view.CategoryNode;
import com.finances.model.Category;
import com.finances.model.User;
import com.finances.service.category.CategoryService;
import com.finances.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/category/view")
public class CategoryViewController {
    private final CategoryService categoryService;
    private final UserService userService;

    @Autowired
    public CategoryViewController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping
    public String viewCategories(@RequestParam("userId") Long userId, Model model) {
        User user = userService.findById(userId);
        List<Category> allCategories = categoryService.getAllCategoriesForUser(user);

        List<CategoryNode> categoryTree = categoryService.buildCategoryTree(allCategories);

        model.addAttribute("user", user);
        model.addAttribute("categories", categoryTree);
        return "category/category";
    }

    /**
     * Создание новой категории с родительской категорией.
     */
    @PostMapping
    public String createCategory(@RequestParam("userId") Long userId,
                                 @RequestParam("name") String name,
                                 @RequestParam(value = "parentCategoryId", required = false) Long parentCategoryId) {
        User user = userService.findById(userId);
        Category parentCategory = parentCategoryId != null ? categoryService.findById(parentCategoryId) : null;

        categoryService.create(name, user, parentCategory);
        return "redirect:/category/view?userId=" + userId;
    }
}
