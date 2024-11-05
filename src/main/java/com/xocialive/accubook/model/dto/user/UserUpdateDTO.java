package com.xocialive.accubook.model.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateDTO {
    @Email(message = "Email should be valid")
    private String email;

    @Size(min = 8, message = "Password should be at least 8 characters")
    private String password;

    @NotNull(message = "Name cannot be empty")
    private String name;
}
