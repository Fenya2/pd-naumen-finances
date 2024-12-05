package com.finances.repository;

import com.finances.model.Category;
import com.finances.model.User;
import jakarta.annotation.Nonnull;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> getByNameAndOwnerAndParent(String name, User user, @Nonnull Category parent);
    List<Category> getByOwnerAndParent(User user, @Nonnull Category parent);
    Category getByOwnerAndDefaultCategoryTrue(User user);
    List<Category> findAllByOwner(User user);
}
