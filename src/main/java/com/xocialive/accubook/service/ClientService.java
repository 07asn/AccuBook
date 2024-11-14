package com.xocialive.accubook.service;

import com.xocialive.accubook.model.dto.client.ClientCreateUpdateDTO;
import com.xocialive.accubook.model.dto.client.ClientDTO;
import com.xocialive.accubook.model.dto.client.ClientWithTotalsDTO;

import java.util.List;
import java.util.Optional;

public interface ClientService {

    ClientDTO                     create(ClientCreateUpdateDTO clientCreateUpdateDTO);
    ClientDTO                     update(Long id, ClientCreateUpdateDTO clientUpdateDTO);
    void                          delete(Long id);
    Optional<ClientDTO>           getClientById(Long id);
    List<ClientCreateUpdateDTO>   getClientsByUserId(Long userId);
    Optional<ClientWithTotalsDTO> getClientWithTotalsById(Long id);
}
