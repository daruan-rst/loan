package com.bank.loan.repository;

import com.bank.loan.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {

    List<Loan> findByNumberOfParcels(int numberOfParcels);
    List<Loan> findByNumberOfParcelsGreaterThan(int numberOfParcels);
}
