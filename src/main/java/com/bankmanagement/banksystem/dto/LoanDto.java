package com.bankmanagement.banksystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoanDto {
    private Long loanId;
    private Long userId;
    private Long bankId;
    
    private String bankName;
    private Long cashSum;
}
