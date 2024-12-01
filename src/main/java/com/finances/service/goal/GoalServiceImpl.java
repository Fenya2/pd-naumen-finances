package com.finances.service.goal;

import com.finances.model.*;
import com.finances.model.Transaction.TransactionType;
import com.finances.repository.GoalRepository;
import com.finances.service.account.AccountService;
import com.finances.service.category.CategoryService;
import com.finances.service.transaction.TransactionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class GoalServiceImpl implements GoalService {
    private final GoalRepository goalRepository;

    private final AccountService accountService;
    private final CategoryService categoryService;
    private final TransactionService transactionService;

    @Autowired
    public GoalServiceImpl(GoalRepository goalRepository,
                           AccountService accountService,
                             CategoryService categoryService,
                           TransactionService transactionService) {
        this.goalRepository = goalRepository;
        this.accountService = accountService;
        this.categoryService = categoryService;
        this.transactionService = transactionService;
    }

    @Override
    @Transactional
    public Goal createGoal(User user, String name, double amount, Date date) {
        final Goal goal = new Goal(user, name, amount, date);

        categoryService.create(name, user,
                categoryService.getDefaultCategoryForUser(user));

        final Account goalAccount = accountService.createGoalAccount(goal);
        goal.setAccount(goalAccount);
        return goalRepository.save(goal);
    }

    @Override
    public void depositFromUserToGoal(User user, Goal goal, Date date, double amount) {
        final Account goalAccount = goal.getAccount();
        final Account userAccount = accountService.getUserAccount(user);

        Category category = categoryService.getGoalCategoryByNameAndOwner(goal, user);

        transactionService.createTransaction(userAccount, category, TransactionType.EXPENSE, date, amount, null);
        transactionService.createTransaction(goalAccount, null, TransactionType.INCOME, date, amount, null);
    }

    @Override
    public void depositFromGoalToUser(Goal goal, User user, Date date, double amount) {
        final Account goalAccount = goal.getAccount();
        final Account userAccount = accountService.getUserAccount(user);

        Category category = categoryService.getGoalCategoryByNameAndOwner(goal, user);

        transactionService.createTransaction(goalAccount, null, TransactionType.EXPENSE, date, amount, null);
        transactionService.createTransaction(userAccount, category, TransactionType.INCOME, date, amount, null);
    }

    @Override
    public void depositFromGoalToGoal(Goal goalFrom, Goal goalTo, Date date, double amount) {
        final Account goalAccountFrom = goalFrom.getAccount();
        final Account goalAccountTo = goalTo.getAccount();

        transactionService.createTransaction(goalAccountFrom, null, TransactionType.EXPENSE, date, amount, null);
        transactionService.createTransaction(goalAccountTo, null, TransactionType.INCOME, date, amount, null);
    }

    @Override
    public Goal findById(long id) {
        return goalRepository.findById(id)
                .orElseThrow(
                        () -> new GoalNotFoundException("Goal with id " + id + " not found")
                );
    }
}
