package com.MindHub.HomeBanking.services;

import com.MindHub.HomeBanking.Models.CardColor;
import com.MindHub.HomeBanking.Models.CardType;
import com.MindHub.HomeBanking.Models.CreditCardLimit;

public interface CreditCardLimitService {

    CreditCardLimit getCurrentLimit(CardType cardType, CardColor cardColor);

    CreditCardLimit getDebit(Long id);
}
