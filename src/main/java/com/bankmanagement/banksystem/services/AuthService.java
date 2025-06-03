package com.bankmanagement.banksystem.services;

import com.bankmanagement.banksystem.dto.LoginUser;

public interface AuthService {
    String login(LoginUser loginDto);
}
