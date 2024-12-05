package com.finances.dto.transaction;

import com.finances.model.Transaction;

import java.util.Date;

public record TransactionGetResponse(long id, long accountId, Transaction.TransactionType type, Date date,
                                     double amount, String description) {
}
