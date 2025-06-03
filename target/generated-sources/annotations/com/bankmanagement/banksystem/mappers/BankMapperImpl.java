package com.bankmanagement.banksystem.mappers;

import com.bankmanagement.banksystem.dto.BankDto;
import com.bankmanagement.banksystem.dto.RegisterBankRequest;
import com.bankmanagement.banksystem.entities.Bank;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-03T15:29:04+0500",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.42.0.v20250514-1000, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class BankMapperImpl implements BankMapper {

    @Override
    public BankDto toDto(Bank bank) {
        if ( bank == null ) {
            return null;
        }

        Long bankId = null;
        String bankName = null;
        String contactNumber = null;
        Long maxDeposit = null;
        Long maxTotalLoans = null;
        Long maxTransfer = null;
        Long maxWithdraw = null;
        int requireYearsCountry = 0;

        bankId = bank.getBankId();
        bankName = bank.getBankName();
        contactNumber = bank.getContactNumber();
        maxDeposit = bank.getMaxDeposit();
        maxTotalLoans = bank.getMaxTotalLoans();
        maxTransfer = bank.getMaxTransfer();
        maxWithdraw = bank.getMaxWithdraw();
        requireYearsCountry = bank.getRequireYearsCountry();

        BankDto bankDto = new BankDto( bankId, bankName, maxTransfer, maxDeposit, maxWithdraw, maxTotalLoans, requireYearsCountry, contactNumber );

        return bankDto;
    }

    @Override
    public Bank toEntity(RegisterBankRequest request) {
        if ( request == null ) {
            return null;
        }

        Bank.BankBuilder bank = Bank.builder();

        bank.bankName( request.getBankName() );
        bank.contactNumber( request.getContactNumber() );
        bank.maxDeposit( request.getMaxDeposit() );
        bank.maxTotalLoans( request.getMaxTotalLoans() );
        bank.maxTransfer( request.getMaxTransfer() );
        bank.maxWithdraw( request.getMaxWithdraw() );
        bank.requireYearsCountry( request.getRequireYearsCountry() );

        return bank.build();
    }
}
