package com.xocialive.accubook.model.dto.transaction;

import com.xocialive.accubook.model.enums.TransactionType;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionUpdateDTO {
    @Positive(message = "Amount must be positive")
    private Double amount;

    @Size(max = 255, message = "Description should be a maximum of 255 characters")
    private String description;

    private LocalDateTime transactionDate;

    private TransactionType type;
}
