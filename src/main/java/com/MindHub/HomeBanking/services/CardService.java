package com.MindHub.HomeBanking.services;

import com.MindHub.HomeBanking.Datos.CardCreditDTO;
import com.MindHub.HomeBanking.Datos.CardDTO;
import com.MindHub.HomeBanking.Models.Account;
import com.MindHub.HomeBanking.Models.Card;

import java.util.List;

public interface CardService {

    List<CardDTO> getCardsDTO();

    List<CardCreditDTO> getCardsCreditDTO();

    CardDTO getCardDTO(long id);

    void saveCards(Card card);

    Account getCardAccount(String number);

    Card getCardByNumber(String card);

}
