package com.bankmanagement.banksystem.dto;

import java.util.Set;

import com.bankmanagement.banksystem.entities.Role;

import lombok.Data;

@Data
public class RegisterUserRequest {
    private String userName;
    private String email;
    private String userLogin;
    private String userPassword;
    private int yearsCountry;
    private int employed;
    private Long totalLoans;
    private Long userBalance;
    private int isAdmin;
    private String contactNumber;
    private Set<Role> roles;
} 
