package com.finances.repository;

import com.finances.model.Goal;
import com.finances.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GoalRepository extends CrudRepository<Goal, Long> {
    List<Goal> getAllByOwner(User owner);
    Optional<Goal> getByNameAndOwner(String name, User owner);
}
