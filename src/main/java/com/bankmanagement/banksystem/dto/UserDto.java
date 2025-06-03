package com.bankmanagement.banksystem.dto;

import java.util.Set;

import com.bankmanagement.banksystem.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long userId;
    private String userName;
    private String email;
    private String userLogin;
    private int yearsCountry;
    private int employed;
    private Long totalLoans;
    private Long userBalance;
    private int isAdmin;
    private String contactNumber;
    private Set<Role> roles;
}
