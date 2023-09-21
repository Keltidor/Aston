package com.example.aston.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {

    @JsonProperty(value = "type")
    private String type;

    @JsonProperty(value = "sum")
    private BigDecimal sum;

    @JsonProperty(value = "date")
    private Date date;

    @JsonProperty(value = "account_id")
    private UUID accountId;
}
