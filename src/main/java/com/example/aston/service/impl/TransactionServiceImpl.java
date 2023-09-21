package com.example.aston.service.impl;

import com.example.aston.dto.TransactionDto;
import com.example.aston.exceptions.BadRequestException;
import com.example.aston.mapper.TransactionMapper;
import com.example.aston.persist.entities.TransactionEntity;
import com.example.aston.persist.repository.TransactionRepository;
import com.example.aston.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionMapper transactionMapper = Mappers.getMapper(TransactionMapper.class);
    private final TransactionRepository transactionRepository;

    public TransactionDto getTransactionById(UUID transactionId) {
        Optional<TransactionEntity> opt = transactionRepository.findById(transactionId);

        if (opt.isPresent()) {
            TransactionEntity transactionEntity = opt.get();
            return transactionMapper.mapToDto(transactionEntity);
        } else {
            throw new BadRequestException("Account with id: " + transactionId + " not found");
        }
    }

    public List<TransactionDto> getAllTransactions() {
        List<TransactionEntity> transactionEntities = getAllTransactionEntities();
        return mapTransactionEntitiesToDto(transactionEntities);
    }

    public List<TransactionDto> getAllTransactionsByAccountId(UUID accountId) {
        List<TransactionEntity> transactionEntities = getAllTransactionEntitiesByAccountId(accountId);
        return mapTransactionEntitiesToDto(transactionEntities);
    }

    private List<TransactionEntity> getAllTransactionEntities() {
        return transactionRepository.findAll();
    }

    private List<TransactionEntity> getAllTransactionEntitiesByAccountId(UUID accountId) {
        return transactionRepository.findAllByBankAccountId(accountId);
    }

    private List<TransactionDto> mapTransactionEntitiesToDto(List<TransactionEntity> transactionEntities) {
        List<TransactionDto> transactions = new ArrayList<>();

        for (TransactionEntity entity : transactionEntities) {
            TransactionDto dto = transactionMapper.mapToDto(entity);
            transactions.add(dto);
        }

        return transactions;
    }
}
