package com.xocialive.accubook.service.impl;

import com.xocialive.accubook.model.dto.transaction.TransactionCreateDTO;
import com.xocialive.accubook.model.dto.transaction.TransactionDTO;
import com.xocialive.accubook.model.dto.transaction.TransactionUpdateDTO;
import com.xocialive.accubook.model.entity.Client;
import com.xocialive.accubook.model.entity.Transaction;
import com.xocialive.accubook.model.mapper.TransactionMapper;
import com.xocialive.accubook.model.repository.ClientRepo;
import com.xocialive.accubook.model.repository.TransactionRepo;
import com.xocialive.accubook.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepo transactionRepository;
    private final ClientRepo clientRepo;
    private final TransactionMapper transactionMapper;

    @Override
    public TransactionDTO create(TransactionCreateDTO transactionCreateDTO, Long clientId) {
        Client client = clientRepo.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Transaction transaction = transactionMapper.toTransaction(transactionCreateDTO);
        transaction.setClient(client);
        transaction.setTransactionDate(getCurrentOrProvidedDate(transaction.getTransactionDate()));

        Transaction savedTransaction = transactionRepository.save(transaction);
        return transactionMapper.toTransactionDTO(savedTransaction);
    }

    @Override
    public TransactionDTO update(Long id, TransactionUpdateDTO transactionUpdateDTO) {
        return transactionRepository.findById(id)
                .map(existingTransaction -> {
                    updateTransactionFields(existingTransaction, transactionUpdateDTO);
                    return transactionMapper.toTransactionDTO(transactionRepository.save(existingTransaction));
                })
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    @Override
    public void delete(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new RuntimeException("Transaction not found");
        }
        transactionRepository.deleteById(id);
    }

    @Override
    public Optional<TransactionDTO> getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .map(transactionMapper::toTransactionDTO);
    }

    @Override
    public List<TransactionDTO> getTransactionsByClientId(Long clientId) {
        return transactionRepository.findByClientId(clientId).stream()
                .map(transactionMapper::toTransactionDTO)
                .collect(Collectors.toList());
    }

    private LocalDateTime getCurrentOrProvidedDate(LocalDateTime providedDate) {
        return providedDate != null ? providedDate : LocalDateTime.now();
    }

    private void updateTransactionFields(Transaction existingTransaction, TransactionUpdateDTO transactionUpdateDTO) {
        if (transactionUpdateDTO.getAmount() != null) {
            existingTransaction.setAmount(transactionUpdateDTO.getAmount());
        }
        if (transactionUpdateDTO.getDescription() != null) {
            existingTransaction.setDescription(transactionUpdateDTO.getDescription());
        }
        if (transactionUpdateDTO.getTransactionDate() != null) {
            existingTransaction.setTransactionDate(transactionUpdateDTO.getTransactionDate());
        }
    }
}
