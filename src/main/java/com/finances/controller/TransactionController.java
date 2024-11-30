package com.finances.controller;

import com.finances.dto.transaction.TransactionCreateRequest;
import com.finances.dto.transaction.TransactionCreateResponse;
import com.finances.dto.transaction.TransactionMapper;
import com.finances.model.Transaction;
import com.finances.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Отвечает за взаимодействие с транзакциями
 */
@RestController
@RequestMapping(value = "/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(value = "create")
    public TransactionCreateResponse createTransaction(@RequestBody TransactionCreateRequest transactionRequest) {
        final Transaction transaction = transactionService.createTransaction(transactionRequest);
        return TransactionMapper.toCreatedResponse(transaction);
    }
}
