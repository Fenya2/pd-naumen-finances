package com.finances.repository;

import org.springframework.data.repository.CrudRepository;

import com.finances.model.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long>
{
    Optional<User> findByLogin(String login);
}
