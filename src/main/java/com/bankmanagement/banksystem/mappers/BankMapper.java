package com.bankmanagement.banksystem.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bankmanagement.banksystem.dto.BankDto;
import com.bankmanagement.banksystem.dto.RegisterBankRequest;
import com.bankmanagement.banksystem.entities.Bank;

@Mapper(componentModel = "spring")
public interface BankMapper {
    BankDto toDto(Bank bank);
    @Mapping(target = "bankId", ignore = true)
    Bank toEntity(RegisterBankRequest request);
}
