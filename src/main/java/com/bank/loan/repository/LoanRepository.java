package com.bank.loan.repository;

import com.bank.loan.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Integer> {

    List<Loan> findByNumberOfParcels(int numberOfParcels);
    List<Loan> findByNumberOfParcelsGreaterThan(int numberOfParcels);
}
