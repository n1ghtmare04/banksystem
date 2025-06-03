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
@Table(name = "loans_history")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loanId")
    private Long loanId;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "bankId")
    private Long bankId;

    @Column(name = "bankName")
    private String bankName;

    @Column(name = "cashSum")
    private Long cashSum;

    @Override
    public String toString() {
        return String.format("Loan Id: %d, User Id: %d, Bank Id: %d, Bank Name: %s, Cash Amount: %d",
            loanId, userId, bankId, bankName, cashSum);
    }
}
