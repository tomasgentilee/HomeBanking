package com.MindHub.HomeBanking.services.implement;

import com.MindHub.HomeBanking.Datos.AccountDTO;
import com.MindHub.HomeBanking.Datos.ClientDTO;
import com.MindHub.HomeBanking.Models.Account;
import com.MindHub.HomeBanking.Repository.AccountRepository;
import com.MindHub.HomeBanking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class AccountServiceImplements implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public List<AccountDTO> getAccounts() {
        return accountRepository.findAll().stream().map(AccountDTO::new).collect(toList());
    }

    @Override
    public AccountDTO getAccountDTO(long id) {
        return accountRepository.findById(id).map(AccountDTO::new).orElse(null);
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public Account findByNumber(String number) {
        return accountRepository.findByNumber(number);
    }

    @Override
    public Account getAccount(long id) {
        return accountRepository.findById(id).orElse(null);
    }
}
