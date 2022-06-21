package com.MindHub.HomeBanking.services.implement;

import com.MindHub.HomeBanking.Models.CardColor;
import com.MindHub.HomeBanking.Models.CardType;
import com.MindHub.HomeBanking.Models.CreditCardLimit;
import com.MindHub.HomeBanking.Repository.CreditCardLimitRepository;
import com.MindHub.HomeBanking.services.CreditCardLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditCardLimitImplements implements CreditCardLimitService {

    @Autowired
    CreditCardLimitRepository creditCardLimitRepository;


    @Override
    public CreditCardLimit getCurrentLimit(CardType cardType, CardColor cardColor) {
        return creditCardLimitRepository.findAll().stream().filter(limit -> limit.getCardColor() == cardColor && cardType== CardType.CREDIT).findFirst().orElse(null);
    }

    @Override
    public CreditCardLimit getDebit(Long id) {
        return creditCardLimitRepository.findById(id).orElse(null);
    }
}
