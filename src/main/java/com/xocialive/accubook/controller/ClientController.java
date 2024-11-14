package com.xocialive.accubook.controller;

import com.xocialive.accubook.annotation.CheckClientAuthorization;
import com.xocialive.accubook.model.dto.client.ClientCreateUpdateDTO;
import com.xocialive.accubook.model.dto.client.ClientDTO;
import com.xocialive.accubook.model.dto.client.ClientWithTotalsDTO;
import com.xocialive.accubook.model.entity.UserPrincipal;
import com.xocialive.accubook.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody ClientCreateUpdateDTO clientCreateUpdateDTO) {
        clientService.create(clientCreateUpdateDTO);
        return ResponseEntity.ok("Client Created Successfully");
    }

    @CheckClientAuthorization
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody ClientCreateUpdateDTO clientUpdateDTO) {
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

    @CheckClientAuthorization
    @GetMapping("/v2/{id}")
    public ResponseEntity<ClientWithTotalsDTO> getClientByIdV2(@PathVariable Long id) {
        return clientService.getClientWithTotalsById(id)
                .map(ResponseEntity::ok) // If client is found, return it with the totals
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)); // If not found, return 404
    }

    @GetMapping("/user")
    public ResponseEntity<List<ClientCreateUpdateDTO>> getClientsByUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getId();
        List<ClientCreateUpdateDTO> clients = clientService.getClientsByUserId(userId);
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
