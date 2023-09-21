package com.example.aston.mapper;

import com.example.aston.dto.TransactionDto;
import com.example.aston.persist.entities.TransactionEntity;
import org.mapstruct.Mapper;

@Mapper
public abstract class TransactionMapper implements AbstractMapper<TransactionDto, TransactionEntity> {
}
