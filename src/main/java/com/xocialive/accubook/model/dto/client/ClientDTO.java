package com.xocialive.accubook.model.dto.client;

import com.xocialive.accubook.model.dto.transaction.TransactionDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ClientDTO {

    private Long id;
    private String name;
    private String phoneNumber;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<TransactionDTO> transactions;
}
