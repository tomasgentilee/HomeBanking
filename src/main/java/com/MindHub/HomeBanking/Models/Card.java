package com.MindHub.HomeBanking.Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private boolean active;

    private CardColor cardColor;

    private CardType cardType;

    private String cardHolder, cardNumber;

    private long cvv;

    private LocalDateTime fromDate, thruDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cardLimit_id")
    private CreditCardLimit creditCardLimit;

    @OneToMany(mappedBy = "card", fetch=FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();

    public Card(){};

    public Card(CardColor cardColor, CardType cardType, String cardHolder, String cardNumber, long cvv, LocalDateTime fromDate, LocalDateTime thruDate, Client client, CreditCardLimit creditCardLimit){
        this.active = true;
        this.cardColor = cardColor;
        this.cardType = cardType;
        this.cardHolder = cardHolder;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
        this.client = client;
        this.creditCardLimit = creditCardLimit;
    }

    public long getId() {
        return id;
    }


    public Set<Transaction> getTransactions() {
        return transactions;
    }
    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public CardColor getCardColor() {
        return cardColor;
    }
    public void setCardColor(CardColor cardColor) {
        this.cardColor = cardColor;
    }

    public CardType getCardType() {
        return cardType;
    }
    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public CreditCardLimit getCreditCardLimit() {
        return creditCardLimit;
    }
    public void setCreditCardLimit(CreditCardLimit creditCardLimit) {
        this.creditCardLimit = creditCardLimit;
    }

    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCardHolder() {
        return cardHolder;
    }
    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public long getCvv() {
        return cvv;
    }
    public void setCvv(long cvv) {
        this.cvv = cvv;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }
    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getThruDate() {
        return thruDate;
    }
    public void setThruDate(LocalDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
}
