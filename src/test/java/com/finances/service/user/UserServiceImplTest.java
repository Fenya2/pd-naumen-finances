package com.finances.service.user;

import com.finances.model.User;
import com.finances.repository.UserRepository;
import com.finances.service.CategoryService;
import com.finances.service.account.AccountService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.when;

class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private CategoryService categoryService;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Эмулируем установку идентификатора пользователя при его сохранении
        when(userRepository.save(Mockito.any(User.class))).thenAnswer(invocation ->
        {
            User user = invocation.getArgument(0);
            user.setId(1);
            return user;
        });
    }

    @Test
    void testCreate() {
        final User user = userService.create("login", "password", "name");
        Assertions.assertEquals(1, user.getId());
        Assertions.assertEquals("name", user.getName());
        Assertions.assertEquals("login", user.getLogin());
        Assertions.assertEquals("password", user.getPassword());
    }

    @Test
    void testUpdateWhenNotExists() {
        final User user = userService.create("login", "password", "name");
        user.setLogin("newLogin");
        user.setPassword("newPassword");
        user.setName("newName");
        userService.update(user);

        Assertions.assertEquals("newLogin", user.getLogin());
        Assertions.assertEquals("newPassword", user.getPassword());
        Assertions.assertEquals("newName", user.getName());
    }

    @Test
    void testUpdateWhenUserWithNewLoginExists() {
        final String oldLogin = "oldLogin";

        final User updatingUser = userService.create("login", "password", "name");
        final User oldUser = new User(oldLogin, "oldPassword", "oldName");

        updatingUser.setLogin(oldLogin);
        when(userRepository.findByLogin("oldLogin")).thenReturn(Optional.of(oldUser));

        Assertions.assertThrows(UserAlreadyExistException.class, () -> userService.update(updatingUser));
    }
}