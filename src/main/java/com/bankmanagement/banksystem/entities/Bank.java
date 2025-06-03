package com.bankmanagement.banksystem.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "banks_list")
public class Bank {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bankId")
    private Long bankId;

    @Column(name = "bankName")
    private String bankName;
    
    @Column(name = "maxTransfer")
    private Long maxTransfer;

    @Column(name = "maxDeposit")
    private Long maxDeposit;

    @Column(name = "maxWithdraw")
    private Long maxWithdraw;

    @Column(name = "maxTotalLoans")
    private Long maxTotalLoans;
    
    @Column(name = "requireYearsCountry")
    private int requireYearsCountry;

    @Column(name = "contactNumber")
    private String contactNumber;

    @Override
    public String toString() {
        return String.format("%d, %s, %d, %d, %d, %d, %d, %s",
        bankId, bankName, maxTransfer, maxDeposit, maxWithdraw, maxTotalLoans, requireYearsCountry, contactNumber);
    }
}
