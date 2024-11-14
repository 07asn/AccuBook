package com.xocialive.accubook.model.dto.client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClientCreateUpdateDTO {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @Size(min = 10, max = 15, message = "Phone number should be between 10 and 15 characters")
    private String phoneNumber;
}
