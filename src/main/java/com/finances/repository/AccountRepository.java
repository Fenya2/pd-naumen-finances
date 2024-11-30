package com.finances.repository;

import com.finances.model.Account;
import com.finances.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepository extends CrudRepository<Account, Long> {
    /**
     * Возвращает основной счет пользователя
     */
    Optional<Account> findByOwnerAndAccountType(User owner, Account.AccountType accountType);
}
