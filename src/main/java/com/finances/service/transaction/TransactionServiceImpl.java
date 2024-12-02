package com.finances.service.transaction;


import com.finances.dto.transaction.TransactionCreateRequest;
import com.finances.model.Account;
import com.finances.model.Category;
import com.finances.model.Transaction;
import com.finances.model.Transaction.TransactionType;
import com.finances.model.User;
import com.finances.repository.TransactionRepository;
import com.finances.service.category.CategoryService;
import com.finances.service.account.AccountService;
import com.finances.service.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final UserService userService;
    private final CategoryService categoryService;

    @Autowired
    TransactionServiceImpl(TransactionRepository transactionRepository,
                           UserService userService,
                           AccountService accountService,
                           CategoryService categoryService) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
        this.accountService = accountService;
        this.categoryService = categoryService;
    }

    @Override
    @Transactional
    public Transaction createTransaction(TransactionCreateRequest transactionCreateRequest) {
        final Transaction transaction = new Transaction();
        final User user = userService.findById(transactionCreateRequest.userId());
        final double amount = transactionCreateRequest.amount();
        final Account account = accountService.getUserAccount(user);
        Category category = categoryService.findByIdSilent(transactionCreateRequest.CategoryId());
        transaction.setCategory(category);

        final TransactionType transactionType = transactionCreateRequest.transactionType();
        switch(transactionType) {
            case INCOME -> {
                accountService.deposit(account, amount);
                transaction.setTransactionType(TransactionType.INCOME);
            }
            case EXPENSE -> {
                accountService.withdraw(account, amount);
                transaction.setTransactionType(TransactionType.EXPENSE);
            }
            default -> throw new TransactionServiceException("invalid transaction type " + transactionType);
        }

        if(category == null && account.getAccountType().equals(Account.AccountType.DEFAULT)) {
            transaction.setCategory(categoryService.getDefaultCategoryForUser(user));
        }
        transaction.setAccount(account);
        transaction.setAmount(transactionCreateRequest.amount());
        transaction.setDate(transactionCreateRequest.date());
        transaction.setDescription(transactionCreateRequest.description());
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction createTransaction(Account account,
                                         Category category,
                                         TransactionType transactionType,
                                         Date date,
                                         double amount,
                                         String description) {
        final Transaction transaction = new Transaction();
        final User user = account.getOwner();

        transaction.setCategory(category);

        switch(transactionType) {
            case INCOME -> {
                accountService.deposit(account, amount);
                transaction.setTransactionType(TransactionType.INCOME);
            }
            case EXPENSE -> {
                accountService.withdraw(account, amount);
                transaction.setTransactionType(TransactionType.EXPENSE);
            }
            default -> throw new TransactionServiceException("invalid transaction type " + transactionType);
        }

        if(category == null && account.getAccountType().equals(Account.AccountType.DEFAULT)) {
            transaction.setCategory(categoryService.getDefaultCategoryForUser(user));
        }
        transaction.setAccount(account);
        transaction.setAmount(amount);
        transaction.setDate(date);
        transaction.setDescription(description);
        return transactionRepository.save(transaction);
    }


    @Override
    public Transaction createInboundTransaction(Account from, Account to, double amount) {
        return null;
    }
}
