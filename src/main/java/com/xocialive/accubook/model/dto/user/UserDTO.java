package com.xocialive.accubook.model.dto.user;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private int id;
    private String email;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
