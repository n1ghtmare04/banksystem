package com.bankmanagement.banksystem.mappers;

import org.mapstruct.Mapper;

import com.bankmanagement.banksystem.dto.LoanDto;
import com.bankmanagement.banksystem.dto.RegisterLoanRequest;
import com.bankmanagement.banksystem.entities.Loan;

@Mapper(componentModel = "spring")
public interface LoanMapper {
    LoanDto toDto(Loan loan);
    Loan toEntity(RegisterLoanRequest request);
}
