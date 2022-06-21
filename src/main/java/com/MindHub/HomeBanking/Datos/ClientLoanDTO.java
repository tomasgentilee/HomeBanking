package com.MindHub.HomeBanking.Datos;


import com.MindHub.HomeBanking.Models.ClientLoan;

public class ClientLoanDTO {

    private long id, loanID;

    private double amount;

    private int payment;

    private String name;


    public ClientLoanDTO (){};

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.amount = clientLoan.getAmount();
        this.payment = clientLoan.getPayment();
        this.loanID = clientLoan.getLoan().getId();
        this.name = clientLoan.getLoan().getName();
    }

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public int getPayment() {
        return payment;
    }

    public long getLoanID() {
        return loanID;
    }

    public String getName() {
        return name;
    }
}
