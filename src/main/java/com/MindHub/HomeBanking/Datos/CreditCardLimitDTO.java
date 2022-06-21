package com.MindHub.HomeBanking.Datos;

import com.MindHub.HomeBanking.Models.CreditCardLimit;

public class CreditCardLimitDTO {

    private double cardLimit, availableBalance;

    public CreditCardLimitDTO (){};

    public CreditCardLimitDTO(CreditCardLimit creditCardLimit) {
        this.cardLimit = creditCardLimit.getCardLimit();
        this.availableBalance = creditCardLimit.getAvailableBalance();
    }

    public double getCardLimit() {
        return cardLimit;
    }
}
