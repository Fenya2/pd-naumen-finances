package com.finances.service.account;

import com.finances.model.Account;
import com.finances.model.Goal;
import com.finances.model.User;

public interface AccountService {
    /**
     * Создает основной счет пользователя. Если он уже существует, поднимает исключение.
     * Возвращает созданный счет
     */
    Account createUserAccount(User user);

    /**
     * Возвращает основной счет пользователя
     */
    Account getUserAccount(User user);

    /**
     * Создаёт счёт для цели
     */
    Account createGoalAccount(Goal goal);

    /**
     * Вносит указанную сумму на указанный счет
     */
    void deposit(Account account, double amount);

    /**
     * Снимает с указанного счета указанную сумму, если это возможно
     */
    void withdraw(Account account, double amount);
}
