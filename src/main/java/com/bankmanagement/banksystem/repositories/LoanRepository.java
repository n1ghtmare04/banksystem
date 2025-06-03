package com.bankmanagement.banksystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankmanagement.banksystem.entities.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
