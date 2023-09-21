package com.example.aston.mapper;

import com.example.aston.dto.BankAccountDto;
import com.example.aston.persist.entities.BankAccountEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public abstract class BankAccountMapper implements AbstractMapper<BankAccountDto, BankAccountEntity> {
}
