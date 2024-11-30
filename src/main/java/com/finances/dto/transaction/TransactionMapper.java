package com.finances.dto.transaction;

import com.finances.model.Transaction;

/**
 * Занимается преобразованием транзакций в различные dto
 */
public class TransactionMapper {
    private TransactionMapper() {}
    public static TransactionDTO toDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getId(),
                transaction.getTransactionType(),
                transaction.getCategory().getId(),
                transaction.getDescription(),
                transaction.getAmount()
        );
    }

    public static TransactionCreateResponse toCreatedResponse(Transaction transaction) {
        return new TransactionCreateResponse(transaction.getId());
    }
}
