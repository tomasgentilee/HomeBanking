package com.MindHub.HomeBanking.Datos;



public class LoanApplicationDTO {

    private long id;

    private Double amount;

    private int payments;

    private String destinyAccount;

    public LoanApplicationDTO (){};

    public LoanApplicationDTO(long id, Double amount, int payments, String destinyAccount) {
        this.id = id;
        this.amount = amount;
        this.payments = payments;
        this.destinyAccount = destinyAccount;
    }

    public long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }

    public String getDestinyAccount() {
        return destinyAccount;
    }
}
