package com.bank.loan.response;

import com.bank.loan.model.Loan;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class LoanResponse {

    int id;
    private String accountId;
    private int numberOfParcels;
    private BigDecimal loanAmmount;
    private BigDecimal tax;
    private BigDecimal parcelAmmount;

    public LoanResponse(Loan loan){
        this.id = loan.getLoanId();
        this.accountId = loan.getAccountId();
        this.numberOfParcels = loan.getNumberOfParcels();
        this.loanAmmount = loan.getLoanAmmount();
        this.tax = loan.getTax();
        this.parcelAmmount = loan.getParcelAmmount();
    }

    public static List<LoanResponse> convert(List<Loan> loans){
        return loans.stream().map(LoanResponse::new).collect(Collectors.toList());
    }
}
