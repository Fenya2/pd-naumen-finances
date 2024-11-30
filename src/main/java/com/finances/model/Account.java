package com.finances.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Счет пользователя
 */
@Data
@Entity
@Table(name = "tbl_account")
public class Account
{
    public enum AccountType
    {
        /**
         * Счет, связанный с пользователем. Существует, пока в системе есть пользователь
         */
        DEFAULT,
        /**
         * Счет, связанный с целью. Существует, пока в системе есть цель
         */
        GOAL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class)
    private User owner;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private Double balance;
}
