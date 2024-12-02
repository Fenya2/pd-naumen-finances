package com.finances.service.goal;

import com.finances.model.*;
import com.finances.model.Transaction.TransactionType;
import com.finances.repository.GoalRepository;
import com.finances.service.account.AccountService;
import com.finances.service.category.CategoryService;
import com.finances.service.transaction.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GoalServiceImplTest {

    @Mock
    private GoalRepository goalRepository;

    @Mock
    private AccountService accountService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private GoalServiceImpl goalService;

    private User user;
    private Goal goal;
    private Account userAccount;
    private Account goalAccount;
    private Category goalCategory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);

        goal = new Goal(user, "Vacation", 1000.0, new Date());
        goalAccount = new Account();
        goal.setAccount(goalAccount);

        userAccount = new Account();

        goalCategory = new Category("Vacation", user, null);
    }

    @Test
    void createGoal_ShouldCreateGoalWithAccountAndCategory() {
        when(categoryService.getDefaultCategoryForUser(user)).thenReturn(goalCategory);
        when(accountService.createGoalAccount(any(Goal.class))).thenReturn(goalAccount);
        when(goalRepository.save(any(Goal.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Goal createdGoal = goalService.createGoal(user, "Vacation", 1000.0, new Date());

        assertNotNull(createdGoal);
        assertEquals("Vacation", createdGoal.getName());
        assertEquals(goalAccount, createdGoal.getAccount());

        verify(categoryService).create(eq("Vacation"), eq(user), eq(goalCategory));
        verify(accountService).createGoalAccount(createdGoal);
        verify(goalRepository).save(createdGoal);
    }

    @Test
    void depositFromUserToGoal_ShouldCreateExpenseAndIncomeTransactions() {
        when(accountService.getUserAccount(user)).thenReturn(userAccount);
        when(categoryService.getGoalCategory(goal)).thenReturn(goalCategory);

        Date date = new Date();
        double amount = 100.0;

        goalService.depositFromUserToGoal(user, goal, date, amount);

        verify(transactionService).createTransaction(eq(userAccount), eq(goalCategory), eq(TransactionType.EXPENSE), eq(date), eq(amount), isNull());
        verify(transactionService).createTransaction(eq(goalAccount), isNull(), eq(TransactionType.INCOME), eq(date), eq(amount), isNull());
    }

    @Test
    void withdrawFromGoalToUser_ShouldCreateExpenseAndIncomeTransactions() {
        when(accountService.getUserAccount(user)).thenReturn(userAccount);
        when(categoryService.getGoalCategory(goal)).thenReturn(goalCategory);

        Date date = new Date();
        double amount = 50.0;

        goalService.withdrawFromGoalToUser(goal, user, date, amount);

        verify(transactionService).createTransaction(eq(goalAccount), isNull(), eq(TransactionType.EXPENSE), eq(date), eq(amount), isNull());
        verify(transactionService).createTransaction(eq(userAccount), eq(goalCategory), eq(TransactionType.INCOME), eq(date), eq(amount), isNull());
    }

    @Test
    void depositFromGoalToGoal_ShouldCreateExpenseAndIncomeTransactions() {
        Goal goalTo = new Goal(user, "Car", 5000.0, new Date());
        Account goalAccountTo = new Account();
        goalTo.setAccount(goalAccountTo);

        Date date = new Date();
        double amount = 200.0;

        goalService.depositFromGoalToGoal(goal, goalTo, date, amount);

        verify(transactionService).createTransaction(eq(goalAccount), isNull(), eq(TransactionType.EXPENSE), eq(date), eq(amount), isNull());
        verify(transactionService).createTransaction(eq(goalAccountTo), isNull(), eq(TransactionType.INCOME), eq(date), eq(amount), isNull());
    }

    @Test
    void findById_ShouldReturnGoalIfExists() {
        when(goalRepository.findById(1L)).thenReturn(Optional.of(goal));

        Goal foundGoal = goalService.findById(1L);

        assertNotNull(foundGoal);
        assertEquals(goal, foundGoal);
        verify(goalRepository).findById(1L);
    }

    @Test
    void findById_ShouldThrowExceptionIfGoalNotFound() {
        when(goalRepository.findById(1L)).thenReturn(Optional.empty());

        GoalNotFoundException exception = assertThrows(GoalNotFoundException.class, () -> goalService.findById(1L));

        assertEquals("Goal with id 1 not found", exception.getMessage());
        verify(goalRepository).findById(1L);
    }
}
