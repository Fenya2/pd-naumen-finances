package com.finances.service.transaction;

import com.finances.dto.transaction.TransactionCreateRequest;
import com.finances.model.Account;
import com.finances.model.Transaction;

/**
 * Сервис управления транзакциями
 */
public interface TransactionService {
    /**
     * Создает внешнюю транзакцию на основе запроса. Если в запросе категория, к которой относится транзакция, не
     * нашлась, относит эту транзакцию к категории пользователя по умолчанию
     */
    Transaction createTransaction(final TransactionCreateRequest transactionCreateRequest);

    /**
     * Осуществляет транзакцию с одного счета на другой (счета необязательно должны принадлежать одному пользователю).
     * Перевод с основного счета пользователя считается расходной транзакцией. Перевод на основной счет пользователя
     * считается доходной транзакцией
     */
    void createInboundTransaction(final Account from, final Account to, final double amount);
}
