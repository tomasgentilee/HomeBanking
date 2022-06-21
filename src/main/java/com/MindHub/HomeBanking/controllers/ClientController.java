package com.MindHub.HomeBanking.controllers;

import com.MindHub.HomeBanking.Datos.ClientDTO;
import com.MindHub.HomeBanking.Models.Account;
import com.MindHub.HomeBanking.Models.AccountType;
import com.MindHub.HomeBanking.Models.Client;
import com.MindHub.HomeBanking.Repository.AccountRepository;
import com.MindHub.HomeBanking.Utilities.Utility;
import com.MindHub.HomeBanking.services.AccountService;
import com.MindHub.HomeBanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientService clientService;
    @Autowired
    private AccountService accountService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/clients")
    public List<ClientDTO> getClients(){

        return clientService.getClientsDTO();

    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return clientService.getClientDTO(id);
    }


    @PostMapping("/clients")
    public ResponseEntity<Object> register(

            @RequestParam String name, @RequestParam String lastName,

            @RequestParam String email, @RequestParam String password) {


        if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {

            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

        }


        if (clientService.getClientByEmail(email) != null) {

            return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);

        }


        Client clientRegister = new Client(name, lastName, email, passwordEncoder.encode(password));
        clientService.saveClient(clientRegister);

        accountService.saveAccount(new Account("VIN" + Utility.getRandomNumber(10000000, 99999999), 0, LocalDateTime.now(), clientRegister, AccountType.Savings));


        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/clients/current")
    public ClientDTO getCurrentClient(Authentication authentication) {
        Client currentClient = clientService.getCurrentClient(authentication);
        return new ClientDTO (currentClient);

    }
}
