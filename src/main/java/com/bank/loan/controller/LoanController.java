package com.bank.loan.controller;


import com.bank.loan.client.AccountClient;
import com.bank.loan.model.Loan;
import com.bank.loan.repository.LoanRepository;
import com.bank.loan.response.LoanResponse;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
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

    private AccountClient accountClient;

    private static final String ACCOUNT_SERVICE ="accountService" ;

    @RateLimiter(name=ACCOUNT_SERVICE, fallbackMethod = "rateLimiterFallback")
    @PostMapping("/new-loan/{loan-id}")
    public ResponseEntity<LoanResponse> newLoan(
            @RequestParam String accountId,
            @RequestParam BigDecimal loanAmmount,
            @RequestParam int numberOfParcels,
            UriComponentsBuilder uriComponentsBuilder){
        BigDecimal tax = new BigDecimal("1.02");
        BigDecimal loanParcelAmmount = loanAmmount.divide(new BigDecimal(numberOfParcels));
        Loan loan = new Loan(0, accountId, numberOfParcels, loanAmmount,tax,loanParcelAmmount);

        URI uri = uriComponentsBuilder.path("/loans/{loan-id}")
                .buildAndExpand(loan.getLoanId()).toUri();
        loanRepository.save(loan);
        accountClient.updateAccount(Integer.parseInt(accountId), loanAmmount);
        return ResponseEntity.created(uri).body(new LoanResponse(loan));
    }

    @RateLimiter(name=ACCOUNT_SERVICE, fallbackMethod = "rateLimiterFallback")
    @PutMapping("/pay-parcel")
    public ResponseEntity<LoanResponse> payLoanParcel(
            @RequestParam int loanId,
            @RequestParam int numberOfParcelsToBePaid){
        Loan loan = loanRepository.getById(loanId);
        if (numberOfParcelsToBePaid >loan.getNumberOfParcels()){
            numberOfParcelsToBePaid = loan.getNumberOfParcels();
        }
        loan.setNumberOfParcels(loan.getNumberOfParcels()- numberOfParcelsToBePaid);
        loanRepository.save(loan);

        BigDecimal ammountToBePaid = loan.getParcelAmmount().multiply(loan.getTax()).multiply(new BigDecimal(numberOfParcelsToBePaid));
        accountClient.updateAccount(Integer.parseInt(loan.getAccountId()) , ammountToBePaid.negate());
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
