package com.finances.repository;

import com.finances.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

/**
 * Тест, проверяющий, что мы правильно написали репозитории
 */
@DataJpaTest
class UserRepositoryTest {
    private final UserRepository userRepository;

    @Autowired
    public UserRepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    void testCreateUser()
    {
        User user = new User();
        user.setId(1L);
        user.setName("test");
        user.setPassword("password");

        userRepository.save(user);

        User user2 = userRepository.findById(1L).orElseThrow();
        Assertions.assertNotSame(user, user2);
        Assertions.assertEquals(user.getName(), user2.getName());
    }
}