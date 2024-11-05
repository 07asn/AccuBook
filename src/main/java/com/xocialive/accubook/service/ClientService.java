package com.xocialive.accubook.service;

import com.xocialive.accubook.model.dto.client.ClientCreateDTO;
import com.xocialive.accubook.model.dto.client.ClientDTO;
import com.xocialive.accubook.model.dto.client.ClientUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    ClientDTO           create(ClientCreateDTO clientCreateDTO);
    ClientDTO           update(Long id, ClientUpdateDTO clientUpdateDTO);
    void                delete(Long id);
    Optional<ClientDTO> getClientById(Long id);
    List<ClientCreateDTO> getClientsByUserId(Long userId);
}
