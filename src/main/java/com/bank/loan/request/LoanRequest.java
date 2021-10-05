package com.bank.loan.request;

import com.bank.loan.model.Loan;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public class LoanRequest {

    int loanId;
    private String accountId;
    private int numberOfParcels;
    private BigDecimal loanAmmount;
    private BigDecimal tax;
    private BigDecimal parcelAmmount;

    public Loan convert(){
        return new Loan(this.loanId, this.accountId, this.numberOfParcels, this.loanAmmount, this.tax, this.parcelAmmount);
    }
}
