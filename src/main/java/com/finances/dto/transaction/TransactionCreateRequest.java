package com.finances.dto.transaction;

import com.finances.model.Transaction;

import java.util.Date;

/**
 * Тело запроса на создание транзакции, поступающей извне, т.е меняющей основной счет пользователя
 */
public record TransactionCreateRequest(Transaction.TransactionType transactionType,
                                       long CategoryId,
                                       long userId,
                                       Date date,
                                       double amount,
                                       String description) {
}
