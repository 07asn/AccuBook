package com.xocialive.accubook.model.dto.transaction;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionCreateDTO {

    @NotNull(message = "Amount is mandatory")
    private Double amount;

    @Size(max = 255, message = "Description should be a maximum of 255 characters")
    private String description;

    private Long clientId;

    @NotNull(message = "Date is mandatory")
    private LocalDateTime transactionDate;
}
