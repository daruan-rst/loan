package com.bank.loan.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Loan {

    @Id
    int id;

    private String accountId;

    private int numberOfParcels;

    private BigDecimal loanAmmount;

    private BigDecimal tax;

    private BigDecimal parcelAmmount;


}
