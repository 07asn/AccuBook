package com.xocialive.accubook.controller;

import com.xocialive.accubook.annotation.CheckClientAuthorization;
import com.xocialive.accubook.annotation.CheckUserAuthorization;
import com.xocialive.accubook.model.dto.client.ClientCreateDTO;
import com.xocialive.accubook.model.dto.client.ClientDTO;
import com.xocialive.accubook.model.dto.client.ClientUpdateDTO;
import com.xocialive.accubook.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody ClientCreateDTO clientCreateDTO) {
        clientService.create(clientCreateDTO);
        return ResponseEntity.ok("Client Created Successfully");
    }

    @CheckClientAuthorization
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody ClientUpdateDTO clientUpdateDTO) {
        clientService.update(id, clientUpdateDTO);
        return ResponseEntity.ok("Client Updated Successfully");
    }

    @CheckClientAuthorization
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        return clientService.getClientById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @CheckUserAuthorization
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ClientCreateDTO>> getClientsByUserId(@PathVariable Long userId) {
        List<ClientCreateDTO> clients = clientService.getClientsByUserId(userId);
        return clients.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList())
                : ResponseEntity.ok(clients);
    }

    @CheckClientAuthorization
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
