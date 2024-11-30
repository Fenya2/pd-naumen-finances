package com.finances.service.account;

import com.finances.model.Account;
import com.finances.model.User;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Override
    public void createDefaultAccountForUser(User user) {
    }

    @Override
    public Account getUserAccount(User user) {
        return null;
    }

    @Override
    public void deposit(Account account, double amount) {

    }

    @Override
    public void withdraw(Account account, double amount) {

    }
}
