package com.bankmanagement.banksystem.mappers;

import com.bankmanagement.banksystem.dto.LoanDto;
import com.bankmanagement.banksystem.dto.RegisterLoanRequest;
import com.bankmanagement.banksystem.entities.Loan;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-03T15:29:04+0500",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.42.0.v20250514-1000, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class LoanMapperImpl implements LoanMapper {

    @Override
    public LoanDto toDto(Loan loan) {
        if ( loan == null ) {
            return null;
        }

        Long loanId = null;
        Long userId = null;
        Long bankId = null;
        String bankName = null;
        Long cashSum = null;

        loanId = loan.getLoanId();
        userId = loan.getUserId();
        bankId = loan.getBankId();
        bankName = loan.getBankName();
        cashSum = loan.getCashSum();

        LoanDto loanDto = new LoanDto( loanId, userId, bankId, bankName, cashSum );

        return loanDto;
    }

    @Override
    public Loan toEntity(RegisterLoanRequest request) {
        if ( request == null ) {
            return null;
        }

        Loan.LoanBuilder loan = Loan.builder();

        loan.bankId( request.getBankId() );
        loan.bankName( request.getBankName() );
        loan.cashSum( request.getCashSum() );
        loan.loanId( request.getLoanId() );
        loan.userId( request.getUserId() );

        return loan.build();
    }
}
