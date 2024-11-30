package com.finances.service.transaction;

import com.finances.dto.transaction.TransactionCreateRequest;
import com.finances.model.Transaction;

/**
 * Сервис управления транзакциями
 */
public interface TransactionService {
    /**
     * Создает внешнюю транзакцию на основе запроса. Если в запросе категория, к которой относится транзакция, не
     * нашлась, относит эту категорию к категории пользователя по умолчанию
     */
    Transaction createTransaction(TransactionCreateRequest transactionCreateRequest);
}
