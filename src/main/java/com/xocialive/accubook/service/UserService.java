package com.xocialive.accubook.service;

import com.xocialive.accubook.model.dto.user.UserCreateDTO;
import com.xocialive.accubook.model.dto.user.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDTO           create(UserCreateDTO userCreateDTO);
    UserDTO           update(Long id, UserCreateDTO userCreateDTO);
    Optional<UserDTO> getUserById(Long id);
    Optional<UserDTO> getUserByEmail(String email);
}
