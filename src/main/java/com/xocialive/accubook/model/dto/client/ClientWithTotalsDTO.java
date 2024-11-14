package com.xocialive.accubook.model.dto.client;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClientWithTotalsDTO {

    private ClientDTO client;
    private BigDecimal totalReceived;
    private BigDecimal totalBorrowed;
}
