package com.example.aston.controller;

import com.example.aston.dto.TransactionDto;
import com.example.aston.exceptions.BadRequestException;
import com.example.aston.exceptions.ErrorDto;
import com.example.aston.persist.entities.BankAccountEntity;
import com.example.aston.service.impl.TransactionServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionServiceImpl transactionService;

    @ApiOperation(value = "Get Transaction by id", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorDto.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorDto.class)
    })
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDto> getTransactionById(@PathVariable UUID id) {
        try {
            transactionService.getTransactionById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BadRequestException e) {
            log.error("Transaction with id: {} not found", id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Get all Transactions of one account", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorDto.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorDto.class)
    })
    @GetMapping("/account_all")
    public ResponseEntity<List<TransactionDto>> getAllTransactionsByAccountId(@RequestParam UUID id) {
        try {
            transactionService.getAllTransactionsByAccountId(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BadRequestException e) {
            log.error("Transaction with id: {} not found", id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Get all Transactions", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "No content"),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorDto.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal server error", response = ErrorDto.class)
    })
    @GetMapping("/all")
    public ResponseEntity<List<TransactionDto>> getAllTransactions(@RequestParam UUID id) {
        try {
            transactionService.getAllTransactions();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (BadRequestException e) {
            log.error("Transaction with id: {} not found", id);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
