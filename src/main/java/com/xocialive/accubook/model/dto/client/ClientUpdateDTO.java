package com.xocialive.accubook.model.dto.client;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClientUpdateDTO {

    @Size(min = 10, max = 15, message = "Phone number should be between 10 and 15 characters")
    private String phoneNumber;

    @NotNull(message = "Name cannot be empty")
    private String name;
}
