package com.bank.loan.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id")
    int loanId;

    private String accountId;

    private int numberOfParcels;

    private BigDecimal loanAmmount;

    private BigDecimal tax;

    private BigDecimal parcelAmmount;


}
