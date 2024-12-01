package com.finances.service.transaction;

import com.finances.dto.transaction.TransactionCreateRequest;
import com.finances.model.Account;
import com.finances.model.Category;
import com.finances.model.Transaction;
import com.finances.model.User;
import com.finances.repository.TransactionRepository;
import com.finances.service.category.CategoryService;
import com.finances.service.account.AccountService;
import com.finances.service.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Calendar;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TransactionServiceImplTest {
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private AccountService accountService;
    @Mock
    private UserService userService;
    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTransactionWhenCategorySet() {
        final Date date = getTestDate();
        TransactionCreateRequest request = new TransactionCreateRequest(
                Transaction.TransactionType.INCOME,
                1L,
                1L,
                date,
                100.0,
                "desc"
        );

        final User user = new User();
        final Account account = new Account();
        final Category category = new Category();

        when(userService.findById(1L)).thenReturn(user);
        when(accountService.getUserAccount(user)).thenReturn(account);
        when(categoryService.findByIdSilent(1)).thenReturn(category);
        when(transactionRepository.save(any())).then(invocation -> {
            Transaction transaction = invocation.getArgument(0);
            transaction.setId(1);
            return transaction;
        });

        final Transaction transaction = transactionService.createTransaction(request);
        Assertions.assertEquals(1L, transaction.getId());
        Assertions.assertEquals(account, transaction.getAccount());
        Assertions.assertEquals(Transaction.TransactionType.INCOME, transaction.getTransactionType());
        Assertions.assertEquals(100, transaction.getAmount());
        Assertions.assertEquals(date, transaction.getDate());
    }

    @Test
    void createTransactionWhenCategoryNotSet() {
        final Date date = getTestDate();
        TransactionCreateRequest request = new TransactionCreateRequest(
                Transaction.TransactionType.INCOME,
                0,
                1L,
                date,
                100.0,
                "desc"
        );

        final User user = new User();
        final Account account = new Account();
        account.setAccountType(Account.AccountType.DEFAULT);
        final Category defaultCategory = new Category();

        when(userService.findById(1L)).thenReturn(user);
        when(accountService.getUserAccount(user)).thenReturn(account);
        when(categoryService.findByIdSilent(0)).thenReturn(null);
        when(categoryService.getDefaultCategoryForUser(user)).thenReturn(defaultCategory);
        when(transactionRepository.save(any())).then(invocation -> {
            Transaction transaction = invocation.getArgument(0);
            transaction.setId(1);
            return transaction;
        });

        final Transaction transaction = transactionService.createTransaction(request);
        Assertions.assertEquals(1L, transaction.getId());
        Assertions.assertEquals(account, transaction.getAccount());
        Assertions.assertEquals(Transaction.TransactionType.INCOME, transaction.getTransactionType());
        Assertions.assertEquals(100, transaction.getAmount());
        Assertions.assertEquals(date, transaction.getDate());
    }

    private static Date getTestDate()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, 3);
        calendar.set(Calendar.DATE, 8);
        calendar.set(Calendar.YEAR, 2024);
        calendar.set(Calendar.HOUR_OF_DAY, 4);
        calendar.set(Calendar.MINUTE, 20);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
}