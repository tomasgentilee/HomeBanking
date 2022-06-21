package com.MindHub.HomeBanking.Repository;

import com.MindHub.HomeBanking.Models.CreditCardLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CreditCardLimitRepository extends JpaRepository<CreditCardLimit, Long> {
}
