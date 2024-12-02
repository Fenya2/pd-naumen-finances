package com.finances.service.user;

import com.finances.model.User;
import com.finances.repository.UserRepository;
import com.finances.service.category.CategoryService;
import com.finances.service.account.AccountService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final AccountService accountService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           CategoryService categoryService,
                           AccountService accountService) {
        this.userRepository = userRepository;
        this.categoryService = categoryService;
        this.accountService = accountService;
    }

    @Override
    @Transactional
    public User create(String login, String password, String name) {
        checkLoginNotExist(login);
        checkPasswordCorrect(password);

        final User user = new User(login, password, name);
        userRepository.save(user);
        categoryService.createDefaultCategoriesForUser(user);
        accountService.createUserAccount(user);
        return user;
    }

    @Override
    public void update(User user) {
        userRepository.findByLogin(user.getLogin()).ifPresent(existingUser -> {
            if (existingUser.getId() != user.getId()) {
                throw new UserAlreadyExistException("User with login " + user.getLogin() + " already exists");
            }
        });
        checkPasswordCorrect(user.getPassword());
        userRepository.save(user);
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    private void checkLoginNotExist(final String login) {
        final Optional<User> optionalUser = userRepository.findByLogin(login);
        if(optionalUser.isPresent()) {
            throw new UserAlreadyExistException("User with login " + login + " already exists");
        }
    }

    private static void checkPasswordCorrect(final String password) {
        final boolean isValid = PasswordValidationHelper.isPasswordValid(password);
        if(!isValid) {
            throw new InvalidPasswordException("Password %s is not valid due to security reason");
        }
    }
}
