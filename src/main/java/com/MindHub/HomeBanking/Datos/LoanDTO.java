package com.MindHub.HomeBanking.Datos;


import com.MindHub.HomeBanking.Models.Loan;

import java.util.ArrayList;
import java.util.List;

public class LoanDTO {

    private long id;
    private String name;

    private double maxAmount, interestRate;

    private List<Integer> payments = new ArrayList<>();

    public LoanDTO(){};

    public LoanDTO(Loan loan) {
        this.id = loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payments = loan.getPayments();
        this.interestRate = loan.getInterestRate();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public double getInterestRate() {
        return interestRate;
    }
}
