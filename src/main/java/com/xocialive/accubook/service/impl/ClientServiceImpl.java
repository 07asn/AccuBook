package com.xocialive.accubook.service.impl;

import com.xocialive.accubook.model.dto.client.ClientCreateDTO;
import com.xocialive.accubook.model.dto.client.ClientDTO;
import com.xocialive.accubook.model.dto.client.ClientUpdateDTO;
import com.xocialive.accubook.model.entity.Client;
import com.xocialive.accubook.model.entity.User;
import com.xocialive.accubook.model.mapper.ClientMapper;
import com.xocialive.accubook.model.repository.ClientRepo;
import com.xocialive.accubook.model.repository.UserRepo;
import com.xocialive.accubook.service.ClientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepo clientRepo;
    private final ClientMapper clientMapper;
    private final UserRepo userRepo;

    @Override
    public ClientDTO create(ClientCreateDTO clientCreateDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = authentication.getName();

        User user = userRepo.findByEmail(currentUserEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Client client = clientMapper.toClient(clientCreateDTO);
        client.setUser(user);

        Client savedClient = clientRepo.save(client);
        return clientMapper.toClientDTO(savedClient);
    }

    @Override
    public ClientDTO update(Long id, ClientUpdateDTO clientUpdateDTO) {
        return clientRepo.findById(id)
                .map(existingClient -> {
                    clientMapper.updateClientFromDTO(clientUpdateDTO, existingClient);

                    Client updatedClient = clientRepo.save(existingClient);
                    return clientMapper.toClientDTO(updatedClient);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client with ID " + id + " not found"));
    }

    @Override
    public void delete(Long id) {
        clientRepo.deleteById(id);
    }

    @Override
    public Optional<ClientDTO> getClientById(Long id) {
        return clientRepo.findById(id)
                .map(clientMapper::toClientDTO);
    }

    @Override
    public List<ClientCreateDTO> getClientsByUserId(Long userId) {
        return clientRepo.findByUserId(userId).stream()
                .map(clientMapper::toClientCreateDTO)
                .collect(Collectors.toList());
    }

}
