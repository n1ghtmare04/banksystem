package com.bankmanagement.banksystem.dto;

import lombok.Data;

@Data
public class ChangePasswordOp {
    private String userLogin;
    private String oldUserPassword;
    private String newUserPassword;
}
