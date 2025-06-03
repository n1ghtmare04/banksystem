package com.bankmanagement.banksystem.dto;

import lombok.Data;

@Data
public class RegisterLoanRequest {
    private Long loanId;
    private Long userId;
    private Long bankId;
    
    private String bankName;
    private Long cashSum;
}
