package com.finances.service.user;

import com.finances.model.User;
import com.finances.repository.UserRepository;
import com.finances.security.UserRole;
import com.finances.service.account.AccountService;
import com.finances.service.category.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           CategoryService categoryService,
                           AccountService accountService,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.categoryService = categoryService;
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User create(String login, String password, String name) {
        checkLoginNotExist(login);
        checkPasswordCorrect(password);
        final String encryptedPassword = passwordEncoder.encode(password);
        final User user = new User(login, encryptedPassword, name);
        user.setUserRole(UserRole.USER);
        userRepository.save(user);
        categoryService.createDefaultCategoriesForUser(user);
        accountService.createUserAccount(user);
        return user;
    }

    @Override
    @Transactional
    public User createAdmin(String login, String password, String name) {
        final User user = create(login, password, name);
        user.setUserRole(UserRole.ADMIN);
        userRepository.save(user);
        return user;
    }

    @Override
    public void update(User user) {
        userRepository.findByLogin(user.getLogin()).ifPresent(existingUser -> {
            if(existingUser.getId() != user.getId()) {
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

    @Override
    public UserDetails loadUserByUsername(final String login) throws UsernameNotFoundException {
        final User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException(login));
        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                Collections.singleton(user.getUserRole().getSpringRole())
        );
    }
}
