package com.MindHub.HomeBanking.Datos;

import com.MindHub.HomeBanking.Models.Account;
import com.MindHub.HomeBanking.Models.AccountType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private long id;

    private AccountType accountType;

    private String number;
    private double balance;
    private String creationDate;

    private Set<TransactionDTO> transaction = new HashSet<>();

    public AccountDTO(){}

    public AccountDTO(Account account){
        this.number = account.getNumber();
        this.balance = account.getBalance();
        this.creationDate = account.getCreationDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));;
        this.id = account.getId();
        this.accountType = account.getAccountType();
        this.transaction = account.getTransactions().stream().filter(transaction1 -> account.isActive() == true).map(TransactionDTO::new).collect(Collectors.toSet());
    }

    public Set<TransactionDTO> getTransaction() {
        return transaction;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public String getNumber() {
        return number;
    }

    public double getBalance() {
        return balance;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public long getId() {
        return id;
    }
}
