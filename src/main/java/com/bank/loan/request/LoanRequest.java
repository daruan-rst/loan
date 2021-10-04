package com.bank.loan.request;

import com.bank.loan.model.Loan;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public class LoanRequest {

    int id;
    private String accountId;
    private int numberOfParcels;
    private BigDecimal loanAmmount;
    private BigDecimal tax;

    public Loan convert(){
        return new Loan(this.id, this.accountId, this.numberOfParcels, this.loanAmmount, this.tax);
    }
}
