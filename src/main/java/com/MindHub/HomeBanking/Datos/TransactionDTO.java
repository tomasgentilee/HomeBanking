package com.MindHub.HomeBanking.Datos;

import com.MindHub.HomeBanking.Models.Transaction;
import com.MindHub.HomeBanking.Models.TransactionType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class TransactionDTO {
    private long id;

    private String description;

    private TransactionType Type;
    private double amount;

    private String date;

    public TransactionDTO(){}

    public TransactionDTO(Transaction transaction){
        this.id = transaction.getId();
        this.description = transaction.getDescription();
        this.amount = transaction.getAmount();
        this.date = transaction.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));;
        this.Type = transaction.getType();
    }

    public long getId() {
        return id;
    }

    public TransactionType getType() {
        return Type;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

}
