package com.xocialive.accubook.model.mapper;

import com.xocialive.accubook.model.dto.transaction.TransactionCreateDTO;
import com.xocialive.accubook.model.dto.transaction.TransactionDTO;
import com.xocialive.accubook.model.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionDTO toTransactionDTO(Transaction transaction);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    Transaction toTransaction(TransactionCreateDTO transactionCreateDTO);
}
