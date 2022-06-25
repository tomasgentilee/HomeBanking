package com.MindHub.HomeBanking.controllers;

import com.MindHub.HomeBanking.Datos.CardCreditDTO;
import com.MindHub.HomeBanking.Datos.CardDTO;
import com.MindHub.HomeBanking.Models.*;
import com.MindHub.HomeBanking.Repository.ClientRepository;
import com.MindHub.HomeBanking.Repository.CreditCardLimitRepository;
import com.MindHub.HomeBanking.Utils.CardUtils;
import com.MindHub.HomeBanking.services.AccountService;
import com.MindHub.HomeBanking.services.CardService;
import com.MindHub.HomeBanking.services.ClientService;
import com.MindHub.HomeBanking.services.CreditCardLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {


    @Autowired
    private CreditCardLimitService creditCardLimitService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private CardService cardService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/cards/{id}")
    public CardDTO getCards(@PathVariable Long id){
        return cardService.getCardDTO(id);

    }

    @GetMapping("/cards")
    public List<CardDTO> getCardsDTO(){
        return cardService.getCardsDTO();
    }

    @GetMapping("/cards/credit")
    public List<CardCreditDTO> getCardsCreditDTO(){
        return cardService.getCardsCreditDTO();
    }

    @Transactional
    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> newCard(Authentication authentication,
    @RequestParam CardColor cardColor, @RequestParam CardType cardType, @RequestParam String password, @RequestParam String linkedAccount) {

        Client currentClient = clientService.getCurrentClient(authentication);

        Set<Card> cards = currentClient.getCards().stream().filter(card1 -> card1.getCardType() == cardType && card1.isActive() == true).collect(Collectors.toSet());

        if (!passwordEncoder.matches(password, currentClient.getPassword())){
            return new ResponseEntity<>("Incorrect password", HttpStatus.FORBIDDEN);
        }

        if (cards.size() > 1){
            return new ResponseEntity<>("You already have 1 card of this type", HttpStatus.FORBIDDEN);
        }


        String cardNumber = CardUtils.getCardNumber(1000, 9999);

        int cvv = CardUtils.getCVV(100, 999);

        if (cardType == CardType.CREDIT){
            CreditCardLimit creditCardLimit = creditCardLimitService.getCurrentLimit(cardType, cardColor);

            cardService.saveCards(new Card(cardColor, cardType, currentClient.getName() + " " + currentClient.getLastName(), cardNumber, cvv, LocalDateTime.now(), LocalDateTime.now().plusYears(5), currentClient, creditCardLimit));
        }

        if (cardType == CardType.DEBIT){
            Account associatedAccount = accountService.findByNumber(linkedAccount);

            Card cardCheckAccount = cardService.getLinkedAccount(associatedAccount);

            if(cardCheckAccount != null){
                return new ResponseEntity<>("This account is already linked to another account", HttpStatus.FORBIDDEN);
            }

            if (associatedAccount.isActive() == false){
                return new ResponseEntity<>("This is not a valid account", HttpStatus.FORBIDDEN);
            }

            cardService.saveCards(new Card(cardColor, cardType, currentClient.getName() + " " + currentClient.getLastName(), cardNumber, cvv, LocalDateTime.now(), LocalDateTime.now().plusYears(5), currentClient, associatedAccount));
        }

        return new ResponseEntity<>("Card created successfully", HttpStatus.CREATED);
    }

    @Transactional
    @PatchMapping("/clients/current/cards")
    public ResponseEntity<Object> deletedCard(Authentication authentication, @RequestParam long id, @RequestParam String password) {

        Client currentClient = clientService.getCurrentClient(authentication);

        Card currentCard = currentClient.getCards().stream().filter(card -> card.getId() == id).findFirst().orElse(null);

        if (!passwordEncoder.matches(password, currentClient.getPassword())){
            return new ResponseEntity<>("Incorrect password", HttpStatus.FORBIDDEN);
        }

        if (!currentCard.isActive() == true){
            return new ResponseEntity<>("This card is already deleted", HttpStatus.FORBIDDEN);
        }

        if (currentCard.getCreditCardLimit().getAvailableBalance() < currentCard.getCreditCardLimit().getCardLimit()){
            return new ResponseEntity<>("You have a debt on this card", HttpStatus.FORBIDDEN);
        }

        currentCard.setActive(false);
        cardService.saveCards(currentCard);

        return new ResponseEntity<>("Card deleted successfully",HttpStatus.OK);
    }

    @Transactional
    @PatchMapping("/editCard")
    public ResponseEntity<Object> editCardAccount(Authentication authentication, @RequestParam String cardNumber, @RequestParam String newLinkAccount){

        Client currentClient = clientService.getCurrentClient(authentication);
        Card currentCard = cardService.getCardByNumber(cardNumber);
        Account newAccount = accountService.findByNumber(newLinkAccount);

        if(currentCard.getCardType() == CardType.CREDIT){
            return new ResponseEntity<>("A credit card can't be linked to an account", HttpStatus.FORBIDDEN);
        }

        if(!currentClient.getCards().contains(currentCard)){
            return new ResponseEntity<>("You don't own this card", HttpStatus.FORBIDDEN);
        }

        if(currentCard.getAccount() == newAccount){
            return new ResponseEntity<>("This account is already linked", HttpStatus.FORBIDDEN);
        }

        if(newAccount.isActive() == false){
            return new ResponseEntity<>("This account is inactive", HttpStatus.FORBIDDEN);
        }

        currentCard.setAccount(newAccount);
        cardService.saveCards(currentCard);

        return new ResponseEntity<>("Account linked successfully", HttpStatus.CREATED);
    }
}
