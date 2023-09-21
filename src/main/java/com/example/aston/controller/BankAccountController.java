package com.example.aston.controller;

import com.example.aston.dto.BankAccountDto;
import com.example.aston.exceptions.BadRequestException;
import com.example.aston.exceptions.ErrorDto;
import com.example.aston.persist.entities.BankAccountEntity;
import com.example.aston.service.impl.BankAccountServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class BankAccountController {

    private final BankAccountServiceImpl bankAccountService;

    @ApiOperation(value = "Create Account", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorDto.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorDto.class)
    })
    @PostMapping("/create")
    public ResponseEntity<UUID> createAccount(@RequestBody BankAccountDto dto) {
        try {
            bankAccountService.createAccount(dto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (BadRequestException e) {
            log.error("Account wasn't created");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Get all Accounts", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorDto.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorDto.class)
    })
    @GetMapping("/all")
    public ResponseEntity<List<BankAccountDto>> getAllAccounts() {
        try {
            bankAccountService.getAllAccounts();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BadRequestException e) {
            log.error("Accounts not found or not exist");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Get one Account", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorDto.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorDto.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<BankAccountEntity> getAccountById(@PathVariable UUID id) {
        try {
            bankAccountService.getAccountById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BadRequestException e) {
            log.error("Account with id: {} not found", id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Delete account")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorDto.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorDto.class)
    })
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteBankAccountById(@RequestParam UUID id) {
        try {
            bankAccountService.deleteBankAccountById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BadRequestException e) {
            log.error("Account with id: {} wasn't deleted", id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Make a deposit", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorDto.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorDto.class)
    })
    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestParam UUID accountId,
                                     @RequestParam BigDecimal amount) {
        try {
            bankAccountService.deposit(accountId, amount);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BadRequestException e) {
            log.error("Deposit was failed");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Make a withdraw", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorDto.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorDto.class)
    })
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestParam UUID accountId,
                                      @RequestParam String pin,
                                      @RequestParam BigDecimal amount) {
        try {
            bankAccountService.withdraw(accountId, pin, amount);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BadRequestException e) {
            log.error("Withdraw was failed");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Make a transfer", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorDto.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorDto.class)
    })
    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestParam UUID sourceAccountId,
                                      @RequestParam String pin,
                                      @RequestParam UUID targetAccountId,
                                      @RequestParam BigDecimal amount) {
        try {
            bankAccountService.transfer(sourceAccountId, pin, targetAccountId, amount);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BadRequestException e) {
            log.error("Transfer was failed");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
