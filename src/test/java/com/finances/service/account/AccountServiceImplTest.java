package com.finances.service.account;

import com.finances.model.Account;
import com.finances.model.User;
import com.finances.repository.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AccountServiceImplTest {
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUserAccount() {
        final User user = new User();
        when(accountRepository.findByOwner(user)).thenReturn(Optional.empty());
        when(accountRepository.save(any())).thenAnswer(invocation -> {
            final Account account = invocation.getArgument(0);
            account.setId(1L);
            return account;
        });

        final Account account = accountService.createUserAccount(user);
        Assertions.assertEquals(1L, account.getId());
        Assertions.assertSame(user, account.getOwner());
        Assertions.assertEquals(0.0, account.getBalance());
        Assertions.assertEquals(Account.AccountType.DEFAULT, account.getAccountType());
    }

    @Test
    void getUserAccount() {
        final User user = new User();
        final Account account = new Account();
        account.setAccountType(Account.AccountType.DEFAULT);

        when(accountRepository.findByOwner(user)).thenReturn(Optional.of(account));

        final Account retrievedAccount = accountService.getUserAccount(user);
        Assertions.assertSame(account, retrievedAccount);
    }

    @Test
    void getUserAccountNotFound() {
        User user = new User();

        when(accountRepository.findByOwner(user)).thenReturn(Optional.empty());

        Assertions.assertThrows(AccountNotFoundException.class, () -> {
            accountService.getUserAccount(user);
        });
    }

    @Test
    void deposit() {
        final Account account = new Account();
        account.setBalance(100.0);

        final double amountToDeposit = 50.0;

        when(accountRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        accountService.deposit(account, amountToDeposit);
        Assertions.assertEquals(150.0, account.getBalance());
    }

    @Test
    void depositNegativeAmount() {
        Account account = new Account();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            accountService.deposit(account, -10.0);
        });
    }

    @Test
    void withdraw() {
        final Account account = new Account();
        account.setBalance(100.0);

        double amountToWithdraw = 30.0;

        when(accountRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        accountService.withdraw(account, amountToWithdraw);
        Assertions.assertEquals(70.0, account.getBalance());
    }

    @Test
    void withdrawInsufficientFunds() {
        final Account account = new Account();
        account.setBalance(20.0);

        Assertions.assertThrows(FundsTransferException.class, () -> {
            accountService.withdraw(account, 30.0);
        });
    }

    @Test
    void withdrawNegativeAmount() {
        final Account account = new Account();

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            accountService.withdraw(account, -10.0);
        });
    }
}
