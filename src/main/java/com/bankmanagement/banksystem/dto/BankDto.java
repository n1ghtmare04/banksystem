package com.bankmanagement.banksystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BankDto {
    private Long bankId;
    private String bankName;
    
    private Long maxTransfer;
    private Long maxDeposit;
    private Long maxWithdraw;
    private Long maxTotalLoans;
    
    private int requireYearsCountry;
    private String contactNumber;
}
