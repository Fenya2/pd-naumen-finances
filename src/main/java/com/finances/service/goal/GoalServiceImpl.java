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
import java.util.List;
import java.util.Optional;

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
        checkGoalExistsForCreate(name, user);
        checkDate(date);
        checkAmount(amount);

        final Goal goal = new Goal(user, name, amount, date);

        categoryService.create(name, user,
                categoryService.getDefaultCategoryForUser(user));

        accountService.createGoalAccount(goal);

        return goalRepository.save(goal);
    }

    @Override
    @Transactional
    public void depositFromUserToGoal(User user, Goal goal, Date date, double amount) {
        final Account goalAccount = goal.getAccount();
        final Account userAccount = accountService.getUserAccount(user);

        Category category = categoryService.getGoalCategory(goal);

        transactionService.createTransaction(userAccount, category, TransactionType.EXPENSE, date, amount, null);
        transactionService.createTransaction(goalAccount, null, TransactionType.INCOME, date, amount, null);
    }

    @Override
    @Transactional
    public void withdrawFromGoalToUser(Goal goal, User user, Date date, double amount) {
        final Account goalAccount = goal.getAccount();
        final Account userAccount = accountService.getUserAccount(user);

        Category category = categoryService.getGoalCategory(goal);

        transactionService.createTransaction(goalAccount, null, TransactionType.EXPENSE, date, amount, null);
        transactionService.createTransaction(userAccount, category, TransactionType.INCOME, date, amount, null);
    }

    @Override
    @Transactional
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

    @Override
    public List<Goal> getAllGoalsForUser(User user) {
        return goalRepository.getAllByOwner(user);
    }

    private void checkGoalExistsForCreate(String name, User owner) {
        Optional<Goal> goal = goalRepository.getByNameAndOwner(name, owner);
        if (goal.isPresent()) {
            throw new GoalAlreadyExistException("Goal with name " + name + " already exists");
        }
    }

    private void checkAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        if (Double.isNaN(amount) || Double.isInfinite(amount)) {
            throw new IllegalArgumentException("Amount must be a valid number");
        }
    }

    private void checkDate(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        Date today = new Date();
        if (date.before(today)) {
            throw new IllegalArgumentException("Date cannot be in the past");
        }
    }
}
