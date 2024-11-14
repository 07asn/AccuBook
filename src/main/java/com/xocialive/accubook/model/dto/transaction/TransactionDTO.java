package com.xocialive.accubook.model.dto.transaction;

import com.xocialive.accubook.model.enums.TransactionType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionDTO {

    private Double amount;
    private String description;
    private LocalDateTime transactionDate;
    private TransactionType type;
}
