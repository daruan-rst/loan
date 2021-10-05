package com.bank.loan.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "account")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account {

    @Id
    @Column(name = "account_id")
    @GeneratedValue
    private int accountId;

    private String userId;

    private String email;

    @Setter
    private BigDecimal money;

    private AccountType accountType;




}
