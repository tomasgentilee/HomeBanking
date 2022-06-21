package com.MindHub.HomeBanking.controllers;


import com.MindHub.HomeBanking.Datos.AccountDTO;
import com.MindHub.HomeBanking.Models.Account;
import com.MindHub.HomeBanking.Models.AccountType;
import com.MindHub.HomeBanking.Models.Client;
import com.MindHub.HomeBanking.Repository.ClientRepository;
import com.MindHub.HomeBanking.services.AccountService;
import com.MindHub.HomeBanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.MindHub.HomeBanking.Utilities.Utility.getRandomNumber;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(){
        return  accountService.getAccounts();
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return accountService.getAccountDTO(id);
    }

    @Autowired
    private ClientRepository clientRepository;

    @PostMapping(path = "/clients/current/accounts")

    public ResponseEntity<Object> newAccount(Authentication authentication, @RequestParam AccountType accountType) {

        Client currentClient = clientService.getCurrentClient(authentication);

        if (accountType == null){
            return new ResponseEntity<>("Incorrect type", HttpStatus.FORBIDDEN);
        }

        if (currentClient.getAccounts().stream().filter(account -> account.isActive()).count() >= 3){
            return new ResponseEntity<>("You already have 3 accounts", HttpStatus.FORBIDDEN);
        }

        accountService.saveAccount(new Account("VIN-" + getRandomNumber(10000000, 99999999), 0, LocalDateTime.now(), currentClient, accountType));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Transactional
    @PatchMapping("/clients/current/accounts")
    public ResponseEntity<Object> deletedAccount(Authentication authentication, @RequestParam long id, @RequestParam String password) {

        Client currentClient = clientService.getCurrentClient(authentication);

        Account currentAccount = currentClient.getAccounts().stream().filter(account -> account.getId() == id).findFirst().orElse(null);

        if (!passwordEncoder.matches(password, currentClient.getPassword())){
            return new ResponseEntity<>("Incorrect password", HttpStatus.FORBIDDEN);
        }

        if(currentAccount.getBalance() > 0){
            return new ResponseEntity<>("Please transfer your balance first", HttpStatus.FORBIDDEN);
        }

        if(!currentClient.getAccounts().contains(currentAccount)){
            return new ResponseEntity<>("This is not your account", HttpStatus.FORBIDDEN);
        }

        currentAccount.setActive(false);
        accountService.saveAccount(currentAccount);

        return new ResponseEntity<>("Account deleted successfully",HttpStatus.OK);
    }

}
