package com.finances.controller;

import com.finances.dto.account.AccountMapper;
import com.finances.dto.account.GetAccountResponse;
import com.finances.model.Account;
import com.finances.model.User;
import com.finances.security.GlobalUserValidator;
import com.finances.service.account.AccountService;
import com.finances.service.user.UserService;
import jakarta.persistence.Access;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.templateparser.markup.HTMLTemplateParser;

import java.nio.file.AccessDeniedException;
import java.security.Principal;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

    private final AccountService accountService;
    private final UserService userService;
    private final GlobalUserValidator globalUserValidator;

    @Autowired
    public AccountController(AccountService accountService,
                             UserService userService,
                             GlobalUserValidator globalUserValidator) {
        this.accountService = accountService;
        this.userService = userService;
        this.globalUserValidator = globalUserValidator;
    }

    @GetMapping(value = "{user_id}")
    public GetAccountResponse getAccount(Principal principal, @PathVariable("user_id") long userId) throws AccessDeniedException {
        if(!globalUserValidator.isValid(principal, userId)) {
            throw new AccessDeniedException("access denied");
        }
        principal.getName();
        final User user = userService.findById(userId);
        final Account account = accountService.getUserAccount(user);
        return AccountMapper.toGetAccountResponse(account);
    }
}
