package com.finances.repository;

import com.finances.model.Category;
import com.finances.model.User;
import jakarta.annotation.Nonnull;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> getByNameAndOwnerAndParent(String name, User user, @Nonnull Category parent);
    Category getCategoryByOwnerAndDefaultCategoryTrue(User user);
    Category getByOwnerAndDefaultCategoryTrue(User user);
}
