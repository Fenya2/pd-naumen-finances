package com.finances.service.account;

import com.finances.model.Account;
import com.finances.model.Account.AccountType;
import com.finances.model.Goal;
import com.finances.model.User;
import com.finances.repository.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public Account createUserAccount(User user) {
        checkAccountIsNotExist(user);
        Account account = new Account();
        account.setOwner(user);
        account.setAccountType(AccountType.DEFAULT);
        account.setBalance(0.0);
        return accountRepository.save(account);
    }

    @Override
    public Account getUserAccount(User user) {
        return accountRepository.findByOwner(user).orElseThrow(() ->
                new AccountNotFoundException("Account for user %s not found".formatted(user))
        );
    }

    @Override
    public Account createGoalAccount(Goal goal) {
        return null;
    }

    @Override
    public void deposit(Account account, double amount) {
        if(amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }

    @Override
    public void withdraw(Account account, double amount) {
        if(amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        final double currentBalance = account.getBalance();
        final double newBalance = currentBalance - amount;
        if (newBalance < 0) {
            throw new FundsTransferException("Not enough funds for withdrawal. Account balance: %s, try to withdraw %s"
                            .formatted(currentBalance, amount));
        }
        account.setBalance(newBalance);
        accountRepository.save(account);
    }

    private void checkAccountIsNotExist(User user) {
        if(accountRepository.findByOwner(user).isPresent()) {
            throw new AccountAlreadyExistException("Account for user %s already exist".formatted(user));
        }
    }
}
