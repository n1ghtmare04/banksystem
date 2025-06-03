package com.bankmanagement.banksystem.dto;

import lombok.Data;

@Data
public class RegisterBankRequest {
    private String bankName;
    
    private Long maxTransfer;
    private Long maxDeposit;
    private Long maxWithdraw;
    private Long maxTotalLoans;
    
    private int requireYearsCountry;
    private String contactNumber;
}
