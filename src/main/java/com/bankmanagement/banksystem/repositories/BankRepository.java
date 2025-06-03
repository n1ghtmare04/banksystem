package com.bankmanagement.banksystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankmanagement.banksystem.entities.Bank;

public interface BankRepository extends JpaRepository<Bank, Long> {
    List<Bank> findByBankName(String bankName);
}
