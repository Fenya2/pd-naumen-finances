package com.finances.controller;

import com.finances.dto.transaction.TransactionCreateRequest;
import com.finances.dto.transaction.TransactionCreateResponse;
import com.finances.dto.transaction.TransactionGetResponse;
import com.finances.dto.transaction.TransactionMapper;
import com.finances.model.Transaction;
import com.finances.service.transaction.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping(value = "{transaction_id}")
    public TransactionGetResponse getTransaction(@PathVariable(value = "transaction_id") Long transactionId)
    {
        final Transaction transaction = transactionService.getById(transactionId);
        return TransactionMapper.toGetResponse(transaction);
    }
}
