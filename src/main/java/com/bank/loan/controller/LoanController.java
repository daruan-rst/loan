package com.bank.loan.controller;


import com.bank.loan.model.Loan;
import com.bank.loan.repository.LoanRepository;
import com.bank.loan.request.LoanRequest;
import com.bank.loan.response.LoanResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/loans")
@AllArgsConstructor
public class LoanController {

    private LoanRepository loanRepository;

    @PostMapping("/new-loan")
    public ResponseEntity<LoanResponse> newLoan(
            @RequestParam String accountId,
            @RequestParam BigDecimal loanAmmount,
            @RequestParam int numberOfParcels,
            UriComponentsBuilder uriComponentsBuilder){
        BigDecimal tax = new BigDecimal("1.02");
        Loan loan = new Loan(0, accountId, numberOfParcels, loanAmmount,tax);

        URI uri = uriComponentsBuilder.path("/loans/{id}")
                .buildAndExpand(loan.getId()).toUri();
        loanRepository.save(loan);
        return ResponseEntity.created(uri).body(new LoanResponse(loan));
    }

    @PutMapping("/pay-parcel")
    public ResponseEntity<LoanResponse> payLoanParcel(
            @RequestParam int loanId,
            @RequestParam int numberOfParcels){
        Loan loan = loanRepository.getById(loanId);
        if (numberOfParcels>loan.getNumberOfParcels()){
            numberOfParcels = loan.getNumberOfParcels();
        }
        loan.setNumberOfParcels(loan.getNumberOfParcels()- numberOfParcels);
        loanRepository.save(loan);
        return ResponseEntity.ok(new LoanResponse(loan));}

    @GetMapping("all-paid-loans")
    public ResponseEntity<List<LoanResponse>> allPaidLoans(){
        List<Loan> paidLoans = loanRepository.findByNumberOfParcels(0);
        return ResponseEntity.ok(LoanResponse.convert(paidLoans));
    }

    @GetMapping("all-unpaid-loans")
    public ResponseEntity<List<LoanResponse>> allNonPaidLoans(){
        List<Loan> unpaidLoans = loanRepository.findByNumberOfParcelsGreaterThan(0);
        return ResponseEntity.ok(LoanResponse.convert(unpaidLoans));
    }

}
