package com.example.aston.service;

import com.example.aston.dto.TransactionDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    TransactionDto getTransactionById(UUID transactionId);

    List<TransactionDto> getAllTransactionsByAccountId(UUID accountId);

    List<TransactionDto> getAllTransactions();
}
