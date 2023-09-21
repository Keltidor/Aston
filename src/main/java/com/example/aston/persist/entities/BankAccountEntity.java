package com.example.aston.persist.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "bank_accounts", schema = "aston")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "account_number")
    private UUID accountNumber;

    @Column(name = "pin_code")
    private String pin;

    @Column(name = "balance")
    private BigDecimal balance;
}
