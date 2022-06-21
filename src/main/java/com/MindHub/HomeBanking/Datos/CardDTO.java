package com.MindHub.HomeBanking.Datos;

import com.MindHub.HomeBanking.Models.Card;
import com.MindHub.HomeBanking.Models.CardColor;
import com.MindHub.HomeBanking.Models.CardType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CardDTO {
    private long id;

    private CardColor cardColor;

    private CardType cardType;

    private String cardHolder, cardNumber;

    private  double creditCardLimit, availableBalance;

    private Set<TransactionDTO> transactions = new HashSet<>();
    private long cvv;

    private String fromDate, thruDate;

    public CardDTO(Card card) {
        this.id = card.getId();
        this.cardColor = card.getCardColor();
        this.cardType = card.getCardType();
        this.cardHolder = card.getCardHolder();
        this.cardNumber = card.getCardNumber();
        this.cvv = card.getCvv();
        this.fromDate = card.getFromDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));;
        this.thruDate = card.getThruDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));;
        this.creditCardLimit = card.getCreditCardLimit().getCardLimit();
        this.availableBalance = card.getCreditCardLimit().getAvailableBalance();
        this.transactions = card.getTransactions().stream().filter(transaction1 -> card.isActive() == true).map(TransactionDTO::new).collect(Collectors.toSet());
    }

    public long getId() {
        return id;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public CardType getCardType() {
        return cardType;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }

    public double getCreditCardLimit() {
        return creditCardLimit;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public long getCvv() {
        return cvv;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getThruDate() {
        return thruDate;
    }
}
