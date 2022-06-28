package com.MindHub.HomeBanking.services.implement;

import com.MindHub.HomeBanking.Datos.CardCreditDTO;
import com.MindHub.HomeBanking.Datos.CardDTO;
import com.MindHub.HomeBanking.Models.Account;
import com.MindHub.HomeBanking.Models.Card;
import com.MindHub.HomeBanking.Models.CardType;
import com.MindHub.HomeBanking.Repository.CardRepository;
import com.MindHub.HomeBanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CardServiceImplements implements CardService {

    @Autowired
    CardRepository cardRepository;

    @Override
    public List<CardDTO> getCardsDTO() {
        return cardRepository.findAll().stream().map(CardDTO::new).collect(toList());
    }

    @Override
    public CardCreditDTO getCardsCreditDTO(long id) {
        return cardRepository.findById(id).map(CardCreditDTO::new).orElse(null);
    }

    @Override
    public CardDTO getCardDTO(long id) {
        return cardRepository.findById(id).map(CardDTO::new).orElse(null);
    }

    @Override
    public void saveCards(Card card) {
        cardRepository.save(card);
    }

    @Override
    public Account getCardAccount(String number) {
        return cardRepository.findByCardNumber(number).getAccount();
    }

    @Override
    public Card getCardByNumber(String card) {
        return cardRepository.findByCardNumber(card);
    }

    @Override
    public Card getLinkedAccount(Account account) {
        return cardRepository.findAll().stream().filter(card1 -> card1.getAccount() == account).findFirst().orElse(null);
    }
}
