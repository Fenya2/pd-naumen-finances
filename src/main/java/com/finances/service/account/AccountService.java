package com.finances.service.account;

import com.finances.model.Account;
import com.finances.model.User;

public interface AccountService {
    void createDefaultAccountForUser(User user);
    Account getUserAccount(User user);
    void deposit(Account account, double amount);
    void withdraw(Account account, double amount);
}
