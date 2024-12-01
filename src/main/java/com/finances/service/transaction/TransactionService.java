package com.finances.service.transaction;

import com.finances.dto.transaction.TransactionCreateRequest;
import com.finances.model.Account;
import com.finances.model.Category;
import com.finances.model.Transaction;
import com.finances.model.Transaction.TransactionType;


import java.util.Date;

/**
 * Сервис управления транзакциями
 */
public interface TransactionService {
    /**
     * Создает внешнюю транзакцию на основе запроса. Если в запросе категория, к которой относится транзакция, не
     * нашлась, относит эту категорию к категории пользователя по умолчанию
     */
    Transaction createTransaction(TransactionCreateRequest transactionCreateRequest);

    Transaction createTransaction(Account owner,
                                  Category category,
                                  TransactionType transactionType,
                                  Date date,
                                  double amount,
                                  String description);

    Transaction createInboundTransaction(Account from, Account to, double amount);
}
