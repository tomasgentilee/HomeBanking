package com.MindHub.HomeBanking.services;

import com.MindHub.HomeBanking.Datos.ClientDTO;
import com.MindHub.HomeBanking.Models.Client;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ClientService {

    List<ClientDTO> getClientsDTO();

    Client getCurrentClient(Authentication authentication);

    ClientDTO getClientDTO(long id);

    void saveClient(Client client);

    Client getClientByEmail(String email);
}
