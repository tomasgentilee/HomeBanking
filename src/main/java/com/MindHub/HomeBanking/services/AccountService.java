package com.MindHub.HomeBanking.services;

import com.MindHub.HomeBanking.Datos.AccountDTO;
import com.MindHub.HomeBanking.Models.Account;

import java.util.List;

public interface AccountService {

    List<AccountDTO> getAccounts();

    AccountDTO getAccountDTO(long id);

    void saveAccount(Account account);

    Account getAccount(long id);

    Account findByNumber(String number);
}
