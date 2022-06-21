package com.MindHub.HomeBanking.services;

import com.MindHub.HomeBanking.Datos.CardDTO;
import com.MindHub.HomeBanking.Models.Card;

import java.util.List;

public interface CardService {

    List<CardDTO> getCardsDTO();

    CardDTO getCardDTO(long id);

    void saveCards(Card card);


}
