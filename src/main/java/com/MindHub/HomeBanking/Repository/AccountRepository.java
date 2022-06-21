package com.MindHub.HomeBanking.Repository;

import com.MindHub.HomeBanking.Models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long>{
    Account findByNumber (String number);
}
