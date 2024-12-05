package com.finances.dto.transaction;

import com.finances.model.Transaction;

/**
 * Занимается преобразованием транзакций в различные dto
 */
public class TransactionMapper {

    private TransactionMapper() {
        throw new UnsupportedOperationException();
    }

    public static TransactionDTO toDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getId(),
                transaction.getType(),
                transaction.getCategory().getId(),
                transaction.getDescription(),
                transaction.getAmount()
        );
    }

    public static TransactionCreateResponse toCreatedResponse(Transaction transaction) {
        return new TransactionCreateResponse(transaction.getId(), transaction.getDescription());
    }

    public static TransactionGetResponse toGetResponse(Transaction transaction) {
        return new TransactionGetResponse(
                transaction.getId(),
                transaction.getAccount().getId(),
                transaction.getType(),
                transaction.getDate(),
                transaction.getAmount(),
                transaction.getDescription()
        );
    }
}
