package com.example.aston.persist.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "transactions", schema = "aston")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "type")
    private String type;

    @Column(name = "sum")
    private BigDecimal sum;

    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private BankAccountEntity bankAccount;
}
