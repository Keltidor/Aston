package com.example.aston.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountDto {

    @JsonProperty(value = "name", required = true)
    private String name;

    @JsonProperty(value = "account_number")
    private UUID accountNumber;

    @JsonProperty(value = "pin", required = true)
    private String pin;

    @JsonProperty(value = "balance")
    private BigDecimal balance;
}
