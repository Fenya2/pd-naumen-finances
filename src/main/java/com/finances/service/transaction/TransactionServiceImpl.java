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

import java.util.Date;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        final Category category = categoryService.findByIdSilent(transactionCreateRequest.CategoryId());
        transaction.setCategory(category);

        final TransactionType transactionType = transactionCreateRequest.transactionType();
        switch(transactionType) {
            case INCOME -> {
                accountService.deposit(account, amount);
                if(category == null) {
                    transaction.setCategory(categoryService.getDefaultIncomeCategoryForUser(user));
                }
                transaction.setTransactionType(TransactionType.INCOME);
            }
            case EXPENSE -> {
                accountService.withdraw(account, amount);
                if(category == null) {
                    transaction.setCategory(categoryService.getDefaultExpenseCategoryForUser(user));
                }
                transaction.setTransactionType(TransactionType.EXPENSE);
            }
            default -> throw new TransactionServiceException("invalid transaction type " + transactionType);
        }
        transaction.setAccount(account);
        transaction.setAmount(transactionCreateRequest.amount());
        transaction.setDate(transactionCreateRequest.date());
        transaction.setDescription(transactionCreateRequest.description());
        return transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void createInboundTransaction(final Account from, final Account to, final double amount) {
        if(Objects.equals(from.getId(), to.getId())) {
            throw new InvalidAccountException("from and to cannot be the same");
        }
        accountService.withdraw(from, amount);
        accountService.deposit(to, amount);

        final Date date = new Date();

        final Transaction transactionFrom = new Transaction();
        transactionFrom.setAccount(from);
        transactionFrom.setTransactionType(TransactionType.EXPENSE);
        transactionFrom.setAmount(amount);
        transactionFrom.setDate(date);

        final Transaction transactionTo = new Transaction();
        transactionTo.setAccount(to);
        transactionTo.setTransactionType(TransactionType.INCOME);
        transactionTo.setAmount(amount);
        transactionTo.setDate(date);

        transactionRepository.save(transactionFrom);
        transactionRepository.save(transactionTo);
    }
}
