package com.MindHub.HomeBanking.Datos;

import com.MindHub.HomeBanking.Models.CardType;

import java.time.LocalDateTime;

public class PaymentDTO {

    private CardType cardType;

    private String cardHolder, cardNumber, accountNumber, description, thruDate;

    private long cvv;

    private double amount;


    public PaymentDTO(){}

    public PaymentDTO(CardType cardType, String cardHolder, String cardNumber, long cvv, double amount, String thruDate, String accountNumber, String description) {
        this.cardType = cardType;
        this.cardHolder = cardHolder;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.amount = amount;
        this.thruDate = thruDate;
        this.accountNumber = accountNumber;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getAccountNumber() {
        return accountNumber;
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

    public long getCvv() {
        return cvv;
    }

    public double getAmount() {
        return amount;
    }

    public String getThruDate() {
        return thruDate;
    }
}
