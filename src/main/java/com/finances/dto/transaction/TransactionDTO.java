package com.finances.dto.transaction;

import com.finances.model.Transaction;

public record TransactionDTO(
        long id,
        Transaction.TransactionType transactionType,
        long categoryId,
        String description,
        Double amount
) {
}

