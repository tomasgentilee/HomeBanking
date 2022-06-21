package com.MindHub.HomeBanking.controllers;

import com.MindHub.HomeBanking.Datos.ClientDTO;
import com.MindHub.HomeBanking.Datos.ClientLoanDTO;
import com.MindHub.HomeBanking.Datos.LoanApplicationDTO;
import com.MindHub.HomeBanking.Datos.LoanDTO;
import com.MindHub.HomeBanking.Models.*;
import com.MindHub.HomeBanking.services.AccountService;
import com.MindHub.HomeBanking.services.ClientService;
import com.MindHub.HomeBanking.services.LoanService;
import com.MindHub.HomeBanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private LoanService loanService;
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/loans")
    public List<LoanDTO> getLoan(){
        return  loanService.getLoansDTO();
    }

    @GetMapping("/clientLoans")
    public List<ClientLoanDTO> getClientLoans(){
        return loanService.getClientLoans();
    }

    @GetMapping("/clientLoans/{id}")
    public ClientLoanDTO getClientLoan(@PathVariable Long id){
        return loanService.getClientLoan(id);
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<Object> newLoan(
            @RequestBody LoanApplicationDTO loanApplicationDTO,
            Authentication authentication){

        Client currentClient = clientService.getCurrentClient(authentication);

        Account destinyAccount = accountService.findByNumber(loanApplicationDTO.getDestinyAccount());

        Loan currentLoan = loanService.getLoan(loanApplicationDTO.getId());

        if(loanApplicationDTO.getId() < 0){
            return new ResponseEntity<>("Wrong Id", HttpStatus.FORBIDDEN);
        }

        if(loanApplicationDTO.getDestinyAccount().isEmpty()){
            return new ResponseEntity<>("Destiny account in empty", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getPayments() <= 0 || loanApplicationDTO.getAmount() < 1000 || loanApplicationDTO.getAmount().isNaN() || loanApplicationDTO.getAmount().isInfinite()){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (currentLoan == null){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if(loanApplicationDTO.getId() != currentLoan.getId()){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (currentLoan.getMaxAmount() < loanApplicationDTO.getAmount()){
            return new ResponseEntity<>("You exceeded the max amount", HttpStatus.FORBIDDEN);
        }

        if (!currentLoan.getPayments().contains(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("This is not an available payment", HttpStatus.FORBIDDEN);
        }

        if (destinyAccount == null){
            return new ResponseEntity<>("This account doesn't exist", HttpStatus.FORBIDDEN);
        }

        if (!currentClient.getAccounts().contains(destinyAccount)){
            return new ResponseEntity<>("You don't own this account", HttpStatus.FORBIDDEN);
        }

        ClientLoan loan = new ClientLoan(loanApplicationDTO.getAmount() * currentLoan.getInterestRate() , loanApplicationDTO.getPayments(), currentClient, currentLoan);
        loanService.saveClientLoan(loan);

        Transaction Credit = new Transaction(loanApplicationDTO.getId() + " loan approved", loanApplicationDTO.getAmount(), LocalDateTime.now(), TransactionType.CREDITO, destinyAccount);
        transactionService.saveTransaction(Credit);

        destinyAccount.setBalance(destinyAccount.getBalance() + loanApplicationDTO.getAmount());
        accountService.saveAccount(destinyAccount);

        return new ResponseEntity<>("Loan successfully created", HttpStatus.CREATED);
    }

    @Transactional
    @PostMapping("/loansAdmin")
    public ResponseEntity<Object> newLoanAdmin(
            @RequestParam String name, @RequestParam double maxAmount, @RequestParam List<Integer> payments, @RequestParam double interestRate){

       if(name == null || maxAmount <= 0 || payments == null || interestRate <= 0) {
           return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
       }

       Loan adminLoan = new Loan (name, maxAmount, payments, interestRate);
       loanService.saveLoan(adminLoan);


        return new ResponseEntity<>("Loan successfully created", HttpStatus.CREATED);
    }
}
