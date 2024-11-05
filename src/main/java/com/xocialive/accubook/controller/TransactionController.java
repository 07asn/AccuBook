package com.xocialive.accubook.controller;

import com.xocialive.accubook.annotation.CheckClientAuthorization;
import com.xocialive.accubook.annotation.CheckTransactionOwnership;
import com.xocialive.accubook.model.dto.transaction.TransactionCreateDTO;
import com.xocialive.accubook.model.dto.transaction.TransactionDTO;
import com.xocialive.accubook.model.dto.transaction.TransactionUpdateDTO;
import com.xocialive.accubook.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @CheckClientAuthorization
    @PostMapping("/client/{clientId}")
    public ResponseEntity<TransactionDTO> create(@PathVariable Long clientId, @RequestBody @Valid TransactionCreateDTO transactionCreateDTO) {
        TransactionDTO createdTransaction = transactionService.create(transactionCreateDTO, clientId);
        return ResponseEntity.ok(createdTransaction);
    }


    @CheckTransactionOwnership
    @PutMapping("/{id}")
    public ResponseEntity<TransactionDTO> update(@PathVariable Long id, @RequestBody @Valid TransactionUpdateDTO transactionUpdateDTO) {
        TransactionDTO updatedTransaction = transactionService.update(id, transactionUpdateDTO);
        return ResponseEntity.ok(updatedTransaction);
    }

    @CheckTransactionOwnership
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @CheckClientAuthorization
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByClientId(@PathVariable Long clientId) {
        List<TransactionDTO> transactions = transactionService.getTransactionsByClientId(clientId);
        return ResponseEntity.ok(transactions);
    }

    @CheckTransactionOwnership
    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable Long id) {
        Optional<TransactionDTO> transaction = transactionService.getTransactionById(id);
        return transaction.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}