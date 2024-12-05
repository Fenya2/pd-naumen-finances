package com.finances.controller;

import com.finances.dto.account.AccountMapper;
import com.finances.dto.account.GetAccountResponse;
import com.finances.model.Account;
import com.finances.model.User;
import com.finances.service.account.AccountService;
import com.finances.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

    private final AccountService accountService;
    private final UserService userService;

    @Autowired
    public AccountController(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @GetMapping(value = "{user_id}")
    public GetAccountResponse getAccount(@PathVariable("user_id") long userId) {
        final User user = userService.findById(userId);
        final Account account = accountService.getUserAccount(user);
        return AccountMapper.toGetAccountResponse(account);
    }
}
