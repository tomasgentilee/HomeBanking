package com.MindHub.HomeBanking.services.implement;

import com.MindHub.HomeBanking.Datos.ClientDTO;
import com.MindHub.HomeBanking.Models.Client;
import com.MindHub.HomeBanking.Repository.ClientRepository;
import com.MindHub.HomeBanking.services.ClientService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ClientServiceImplements implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<ClientDTO> getClientsDTO(){
        return clientRepository.findAll().stream().map(ClientDTO::new).collect(toList());
    }

    @Override
    public Client getCurrentClient(Authentication authentication){
        return clientRepository.findByEmail(authentication.getName());
    }

    @Override
    public ClientDTO getClientDTO(long id){
        return clientRepository.findById(id).map(ClientDTO::new).orElse(null);
    }

    @Override
    public void saveClient(Client client){
        clientRepository.save(client);
    };

    @Override
    public Client getClientByEmail(String email){
        return clientRepository.findByEmail(email);
    };

}
