package com.MindHub.HomeBanking.Models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue
    @GenericGenerator(name = "native", strategy = "native")
    private long id;


    private TransactionType Type;

    private String description;

    private double amount;

    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="card_id")
    private Card card;

    public Transaction(){}

    public Transaction(String description, double amount, LocalDateTime date, TransactionType Type , Account account) {

        this.description = description;
        this.amount = amount;
        this.date = date;
        this.Type = Type;
        this.account = account;

    }

    public Transaction(String description, double amount, LocalDateTime date, TransactionType Type, Card card) {

        this.description = description;
        this.amount = amount;
        this.date = date;
        this.Type = Type;
        this.card = card;

    }

    public long getId() {
        return id;
    }

    public TransactionType getType() {
        return Type;
    }

    public void setType(TransactionType type) {
        Type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
