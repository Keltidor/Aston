package com.example.aston.service.impl;

import com.example.aston.dto.BankAccountDto;
import com.example.aston.exceptions.BadRequestException;
import com.example.aston.mapper.BankAccountMapper;
import com.example.aston.persist.entities.BankAccountEntity;
import com.example.aston.persist.repository.BankAccountRepository;
import com.example.aston.service.BankAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {

    private final BankAccountMapper bankAccountMapper = Mappers.getMapper(BankAccountMapper.class);
    private final BankAccountRepository bankAccountRepository;

    @Override
    public UUID createAccount(BankAccountDto dto) {
        BankAccountEntity entity = bankAccountMapper.mapToEntity(dto);
        UUID id = UUID.randomUUID();
        entity.setId(id);
        UUID accountNumber = UUID.randomUUID();
        entity.setAccountNumber(accountNumber);
        entity.setBalance(BigDecimal.ZERO);
        bankAccountRepository.save(entity);
        log.info("Account with id: {} was created", id);
        return id;
    }

    @Override
    public List<BankAccountDto> getAllAccounts() {
        List<BankAccountEntity> accountEntities = bankAccountRepository.findAll();
        List<BankAccountDto> accounts = new ArrayList<>();

        for (BankAccountEntity entity : accountEntities) {
            BankAccountDto dto = bankAccountMapper.mapToDto(entity);
            accounts.add(dto);
        }

        return accounts;
    }

    @Override
    public BankAccountEntity getAccountById(UUID accountId) {
        Optional<BankAccountEntity> opt = bankAccountRepository.findById(accountId);

        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new IllegalArgumentException("Аккаунт с указанным ID не найден.");
        }
    }

    @Override
    public void deleteBankAccountById(UUID id) {
        Optional<BankAccountEntity> opt = bankAccountRepository.findById(id);

        if (opt.isPresent()) {
            opt.ifPresent(bankAccountRepository::delete);
            log.info("Account with id: {} was deleted", id);
        } else {
            throw new BadRequestException("Failed to delete account with id: " + id);
        }
    }

    public void deposit(UUID accountId, BigDecimal amount) {
        BankAccountEntity accountEntity = getAccountById(accountId);
        if (accountEntity != null) {
            // Проверка наличия средств на счете
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                throw new BadRequestException("The replenishment amount must be positive");
            }

            // Увеличение баланса
            BigDecimal newBalance = accountEntity.getBalance().add(amount);
            accountEntity.setBalance(newBalance);

            // Сохранение изменений
            bankAccountRepository.save(accountEntity);
            log.info("Balance successfully replenished");
        } else {
            throw new BadRequestException("Account with id: " + accountId + " not found");
        }
    }

    public void withdraw(UUID accountId, String pin, BigDecimal amount) {
        BankAccountEntity accountEntity = getAccountById(accountId);
        if (accountEntity != null) {
            // Проверка правильности PIN-кода
            if (!pin.equals(accountEntity.getPin())) {
                throw new BadRequestException("Wrong pin");
            }

            // Проверка наличия достаточных средств на счете
            if (amount.compareTo(accountEntity.getBalance()) > 0) {
                throw new BadRequestException("Not enough money on account");
            }

            // Уменьшение баланса
            BigDecimal newBalance = accountEntity.getBalance().subtract(amount);
            accountEntity.setBalance(newBalance);

            // Сохранение изменений
            bankAccountRepository.save(accountEntity);
            log.info("Funds were successfully withdrawn from the account");
        } else {
            throw new BadRequestException("Account with id: " + accountId + " not found");
        }
    }

    public void transfer(UUID sourceAccountId, String pin, UUID targetAccountId, BigDecimal amount) {
        BankAccountEntity sourceAccount = getAccountById(sourceAccountId);
        BankAccountEntity targetAccount = getAccountById(targetAccountId);

        if (sourceAccount != null && targetAccount != null) {
            // Проверка правильности PIN-кода для отправителя
            if (!pin.equals(sourceAccount.getPin())) {
                throw new BadRequestException("Wrong pin");
            }

            // Проверка наличия достаточных средств на счете отправителя
            if (amount.compareTo(sourceAccount.getBalance()) > 0) {
                throw new BadRequestException("Not enough money in the sender's account");
            }

            // Уменьшение баланса отправителя и увеличение баланса получателя
            BigDecimal sourceNewBalance = sourceAccount.getBalance().subtract(amount);
            BigDecimal targetNewBalance = targetAccount.getBalance().add(amount);

            sourceAccount.setBalance(sourceNewBalance);
            targetAccount.setBalance(targetNewBalance);

            // Сохранение изменений
            bankAccountRepository.saveAll(Arrays.asList(sourceAccount, targetAccount));
            log.info("Transfer of funds was successful");
        } else {
            throw new BadRequestException("One of the accounts was not found.");
        }
    }
}
