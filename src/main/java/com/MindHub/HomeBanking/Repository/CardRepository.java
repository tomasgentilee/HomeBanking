package com.MindHub.HomeBanking.Repository;

import com.MindHub.HomeBanking.Models.Card;
import com.MindHub.HomeBanking.Models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CardRepository extends JpaRepository<Card, Long> {

    Card findByCardNumber(String cardNumber);
}
