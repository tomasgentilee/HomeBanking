package com.MindHub.HomeBanking.Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class CreditCardLimit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private CardColor cardColor;

    private double cardLimit, availableBalance;

    @OneToOne(mappedBy = "creditCardLimit", fetch=FetchType.EAGER)
    private Card card;

    public CreditCardLimit(){}

    public CreditCardLimit(CardColor cardColor, double cardLimit, double availableBalance) {
        this.cardColor = cardColor;
        this.cardLimit = cardLimit;
        this.availableBalance = availableBalance;
    }

    public CreditCardLimit(double cardLimit) {
        this.cardLimit = cardLimit;
    }

    public long getId() {
        return id;
    }

    public double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(double availableBalance) {
        this.availableBalance = availableBalance;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public void setCardColor(CardColor cardColor) {
        this.cardColor = cardColor;
    }

    public double getCardLimit() {
        return cardLimit;
    }

    public void setCardLimit(double cardLimit) {
        this.cardLimit = cardLimit;
    }
}
