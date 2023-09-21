package com.example.aston.service;

import com.example.aston.dto.BankAccountDto;
import com.example.aston.persist.entities.BankAccountEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


public interface BankAccountService {

    UUID createAccount(BankAccountDto dto);

    List<BankAccountDto> getAllAccounts();

    BankAccountEntity getAccountById(UUID id);

    void deleteBankAccountById(UUID id);

    void deposit(UUID accountId, BigDecimal amount);

    void withdraw(UUID accountId, String pin, BigDecimal amount);

    void transfer(UUID sourceAccountId, String pin, UUID targetAccountId, BigDecimal amount);
}
