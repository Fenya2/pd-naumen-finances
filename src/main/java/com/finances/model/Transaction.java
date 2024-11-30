package com.finances.model;

import java.util.Date;

import com.finances.model.category.Category;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "tbl_transaction")
public class Transaction
{
    enum TransactionType
    {
        INCOME,
        EXPENSE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = Account.class)
    private Account account;

    /**
     * Категория, к которой относится транзакция
     */
    @ManyToOne
    private Category category;

    /**
     * Время осуществления транзакции
     */
    private Date date;

    /**
     * Сумма транзакции
     */
    private double amount;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private String description;
}
