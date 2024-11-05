package com.xocialive.accubook.service;

import com.xocialive.accubook.model.dto.transaction.TransactionCreateDTO;
import com.xocialive.accubook.model.dto.transaction.TransactionDTO;
import com.xocialive.accubook.model.dto.transaction.TransactionUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface TransactionService {

    TransactionDTO           create(TransactionCreateDTO transactionCreateDTO, Long clientId);
    TransactionDTO           update(Long id, TransactionUpdateDTO transactionUpdateDTO);
    void                     delete(Long id);
    Optional<TransactionDTO> getTransactionById(Long id);
    List<TransactionDTO>     getTransactionsByClientId(Long clientId);

}
