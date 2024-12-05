package com.finances.repository;

import com.finances.model.Transaction;
import com.finances.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    @Query("""
       SELECT t.category.name AS categoryName, SUM(t.amount) AS totalAmount
       FROM Transaction t
       WHERE t.account.owner.id = :userId
         AND t.date BETWEEN :startDate AND :endDate
         AND t.type = 'EXPENSE'
       GROUP BY t.category.name
       ORDER BY totalAmount DESC
       """)
    List<Object[]> getExpensesByCategoryForPeriod(
            @Param("userId") long userId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );

    @Query("""
       SELECT t.category.name AS categoryName, SUM(t.amount) AS totalAmount
       FROM Transaction t
       WHERE t.account.owner.id = :userId
         AND t.date BETWEEN :startDate AND :endDate
         AND t.type = 'INCOME'
       GROUP BY t.category.name
       ORDER BY totalAmount DESC
       """)
    List<Object[]> getIncomeByCategoryForPeriod(
            @Param("userId") long userId,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );

    List<Transaction> gelAllByOwner(User owner);
}
