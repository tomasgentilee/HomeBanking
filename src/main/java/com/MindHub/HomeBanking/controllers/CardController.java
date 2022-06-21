package com.MindHub.HomeBanking.controllers;

import com.MindHub.HomeBanking.Datos.CardDTO;
import com.MindHub.HomeBanking.Models.*;
import com.MindHub.HomeBanking.Repository.ClientRepository;
import com.MindHub.HomeBanking.Repository.CreditCardLimitRepository;
import com.MindHub.HomeBanking.Utils.CardUtils;
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
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {


    @Autowired
    private CreditCardLimitService creditCardLimitService;

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

    @Autowired
    private ClientRepository clientRepository;

    @Transactional
    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> newCard(Authentication authentication,
    @RequestParam CardColor cardColor, @RequestParam CardType cardType, @RequestParam String password) {

        Client currentClient = clientService.getCurrentClient(authentication);

        Set<Card> cards = currentClient.getCards().stream().filter(card1 -> card1.getCardType() == cardType && card1.isActive() == true).collect(Collectors.toSet());

        CreditCardLimit creditCardLimit = creditCardLimitService.getCurrentLimit(cardType, cardColor);

        CreditCardLimit debitLimit = creditCardLimitService.getDebit(4L);

        if (!passwordEncoder.matches(password, currentClient.getPassword())){
            return new ResponseEntity<>("Incorrect password", HttpStatus.FORBIDDEN);
        }

        if (cards.size() >= 3){
            return new ResponseEntity<>("You already have 3 cards of this type", HttpStatus.FORBIDDEN);
        }

        String cardNumber = CardUtils.getCardNumber(1000, 9999);

        int cvv = CardUtils.getCVV(100, 999);

        if (cardType == CardType.CREDIT){
            cardService.saveCards(new Card(cardColor, cardType, currentClient.getName() + " " + currentClient.getLastName(), cardNumber, cvv, LocalDateTime.now(), LocalDateTime.now().plusYears(5), currentClient, creditCardLimit));
        }

        if (cardType == CardType.DEBIT){
            cardService.saveCards(new Card(cardColor, cardType, currentClient.getName() + " " + currentClient.getLastName(), cardNumber, cvv, LocalDateTime.now(), LocalDateTime.now().plusYears(5), currentClient, debitLimit));
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


//    @DeleteMapping("/clients/current/cards")
//    public ResponseEntity<Object> deleteCard(Authentication authentication, @RequestParam String cardNumber) {
//
//        Client currentClient = clientRepository.findByEmail(authentication.getName());
//
//        Card currentCard = currentClient.getCards().stream().filter(card -> card.getCardNumber() == cardNumber).findFirst().orElse(null);
//
//        if (currentCard == null) {
//            return new ResponseEntity<>("This card does not exist", HttpStatus.FORBIDDEN);
//        }
//
//        if(!currentClient.getCards().contains(currentCard)){
//            return new ResponseEntity<>("You do not own this card", HttpStatus.FORBIDDEN);
//        }
//
//        cardRepository.delete(currentCard);
//
//        return new ResponseEntity<>("Card deleted", HttpStatus.OK);
//    }

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
}
