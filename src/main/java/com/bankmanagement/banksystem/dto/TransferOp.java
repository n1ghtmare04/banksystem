package com.bankmanagement.banksystem.dto;

import lombok.Data;

@Data
public class TransferOp {
    private Long clientId;
    private Long transferSum;
}
