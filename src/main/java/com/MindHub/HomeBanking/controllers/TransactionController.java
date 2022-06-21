package com.MindHub.HomeBanking.controllers;

import com.MindHub.HomeBanking.Datos.LoanApplicationDTO;
import com.MindHub.HomeBanking.Datos.PaymentDTO;
import com.MindHub.HomeBanking.Datos.TransactionDTO;
import com.MindHub.HomeBanking.Models.*;
import com.MindHub.HomeBanking.Repository.CardRepository;
import com.MindHub.HomeBanking.services.AccountService;
import com.MindHub.HomeBanking.services.CardService;
import com.MindHub.HomeBanking.services.ClientService;
import com.MindHub.HomeBanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardService cardService;

    @GetMapping("/transactions")
    public List<TransactionDTO> getTransaction (){
        return transactionService.getTransactionsDTO();
    }

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> register(

            Authentication authentication,

            @RequestParam String numberAccountDestinatario, @RequestParam Double amount,

            @RequestParam String description, @RequestParam String ownAccountnumber) {

        Client currentClient = clientService.getCurrentClient(authentication);

        Account ownAccount = accountService.findByNumber(ownAccountnumber);

        Account destinyAccount = accountService.findByNumber(numberAccountDestinatario);

        if (numberAccountDestinatario.isEmpty()) {

            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

        }

        if(destinyAccount.isActive() == false){
            return new ResponseEntity<>("You can't transfer to an inactive account", HttpStatus.FORBIDDEN);
        }

        if (amount < 0 || amount.isNaN() || amount.isInfinite()){
            return new ResponseEntity<>("Incorrect amount value", HttpStatus.FORBIDDEN);
        }

        if (description.isEmpty()){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (ownAccountnumber.isEmpty()){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (numberAccountDestinatario.equals(ownAccountnumber)) {

            return new ResponseEntity<>("You can't transfer to the same account number", HttpStatus.FORBIDDEN);

        }

        if (ownAccount == null) {

            return new ResponseEntity<>("Your account doesn't exist", HttpStatus.FORBIDDEN);

        }

        if (destinyAccount == null) {

            return new ResponseEntity<>("You cannot transfer to a non-existent account", HttpStatus.FORBIDDEN);

        }

        if (!currentClient.getAccounts().contains(ownAccount)){

            return new ResponseEntity<>("This is not your account", HttpStatus.FORBIDDEN);

        }

        if (ownAccount.getBalance() < amount) {

            return new ResponseEntity<>("You don't have enough funds", HttpStatus.FORBIDDEN);

        }

        Transaction Debit = new Transaction(description, amount * -1, LocalDateTime.now(), TransactionType.DEBITO, ownAccount);
        transactionService.saveTransaction(Debit);

        Transaction Credit = new Transaction(description, amount, LocalDateTime.now(), TransactionType.CREDITO, destinyAccount);
        transactionService.saveTransaction(Credit);

        ownAccount.setBalance(ownAccount.getBalance() - amount);

        accountService.saveAccount(ownAccount);

        destinyAccount.setBalance(destinyAccount.getBalance() + amount);

        accountService.saveAccount(destinyAccount);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Transactional
    @PatchMapping("/cardTransaction")
    public ResponseEntity<Object> newTransaction(
            @RequestBody PaymentDTO paymentDTO,
            Authentication authentication){

        Client currentClient = clientService.getCurrentClient(authentication);

        Account ownAccount = accountService.findByNumber(paymentDTO.getAccountNumber());

        Card currentCard = cardRepository.findByCardNumber(paymentDTO.getCardNumber());


        if(currentCard.getCardType() == CardType.CREDIT){
            if(paymentDTO.getCardNumber().isEmpty() || paymentDTO.getCardHolder().isEmpty() || paymentDTO.getCardType() == null){
                return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
            }

            if(currentCard.isActive() == false){
                return new ResponseEntity<>("This card is inactive", HttpStatus.FORBIDDEN);
            }

            if(paymentDTO.getAmount() > currentCard.getCreditCardLimit().getAvailableBalance()){
                return new ResponseEntity<>("Not enough funds", HttpStatus.FORBIDDEN);
            }

            if(paymentDTO.getAmount() <= 0){
                return new ResponseEntity<>("This is not a valid amount", HttpStatus.FORBIDDEN);
            }

            if(currentCard.getCvv() != paymentDTO.getCvv()){
                return new ResponseEntity<>("Incorrect CVV number", HttpStatus.FORBIDDEN);
            }

            if(currentCard.getThruDate().isBefore(LocalDateTime.now())){
                return new ResponseEntity<>("This card has expired", HttpStatus.FORBIDDEN);
            }

            if(currentCard.getCardNumber() == null){
                return new ResponseEntity<>("This card doesn't exist", HttpStatus.FORBIDDEN);
            }

            if(!currentCard.getCardHolder().equals(paymentDTO.getCardHolder())){
                return new ResponseEntity<>("Incorrect card holder", HttpStatus.FORBIDDEN);
            }

            Transaction Debit = new Transaction(paymentDTO.getDescription(), paymentDTO.getAmount(), LocalDateTime.now(), TransactionType.DEBITO, currentCard);
            transactionService.saveTransaction(Debit);

            currentCard.getCreditCardLimit().setAvailableBalance(currentCard.getCreditCardLimit().getAvailableBalance() - paymentDTO.getAmount());
            cardService.saveCards(currentCard);

        }

        if (currentCard.getCardType() == CardType.DEBIT){
            if(currentCard.getCardNumber() == null){
                return new ResponseEntity<>("This card doesn't exist", HttpStatus.FORBIDDEN);
            }

            if(!currentCard.getCardHolder().equals(paymentDTO.getCardHolder())){
                return new ResponseEntity<>("Incorrect card holder", HttpStatus.FORBIDDEN);
            }

            if(currentCard.getCvv() != paymentDTO.getCvv()){
                return new ResponseEntity<>("Incorrect CVV number", HttpStatus.FORBIDDEN);
            }

            if(currentCard.getThruDate().isBefore(LocalDateTime.now())){
                return new ResponseEntity<>("This card has expired", HttpStatus.FORBIDDEN);
            }

            if(paymentDTO.getAmount() <= 0){
                return new ResponseEntity<>("This is not a valid amount", HttpStatus.FORBIDDEN);
            }
            if(paymentDTO.getAmount() > ownAccount.getBalance()){
                return new ResponseEntity<>("You exceed the max amount", HttpStatus.FORBIDDEN);
            }

            Transaction Debit = new Transaction(paymentDTO.getDescription(), paymentDTO.getAmount(), LocalDateTime.now(), TransactionType.DEBITO, currentCard);
            transactionService.saveTransaction(Debit);

            ownAccount.setBalance(ownAccount.getBalance() - paymentDTO.getAmount());
            accountService.saveAccount(ownAccount);

        }

        return new ResponseEntity<>("Transaction successfully done", HttpStatus.CREATED);
    }
}
