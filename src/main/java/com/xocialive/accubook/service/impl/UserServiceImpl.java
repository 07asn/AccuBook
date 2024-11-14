package com.xocialive.accubook.service.impl;

import com.xocialive.accubook.model.dto.user.UserCreateDTO;
import com.xocialive.accubook.model.dto.user.UserDTO;
import com.xocialive.accubook.model.entity.User;
import com.xocialive.accubook.model.mapper.UserMapper;
import com.xocialive.accubook.model.repository.UserRepo;
import com.xocialive.accubook.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public UserDTO create(UserCreateDTO userCreateDTO) {
        userCreateDTO.setPassword(encoder.encode(userCreateDTO.getPassword()));
        User user = userMapper.toUser(userCreateDTO);
        User savedUser = userRepo.save(user);
        return userMapper.toUserDTO(savedUser);
    }

    @Override
    public UserDTO update(Long id, UserCreateDTO userCreateDTO) {
        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (userRepo.existsByEmailAndIdNot(userCreateDTO.getEmail(), id)) {
            throw new IllegalArgumentException("User with this email already exists.");
        }

        if (userCreateDTO.getPassword() != null && !userCreateDTO.getPassword().isEmpty()) {
            existingUser.setPassword(encoder.encode(userCreateDTO.getPassword()));
        }

        userMapper.updateUserFromDto(userCreateDTO, existingUser);

        User updatedUser = userRepo.save(existingUser);
        return userMapper.toUserDTO(updatedUser);
    }

    @Override
    public Optional<UserDTO> getUserById(Long id) {
        return userRepo.findById(id)
                .map(userMapper::toUserDTO);
    }

    @Override
    public Optional<UserDTO> getUserByEmail(String email) {
        return userRepo.findByEmail(email)
                .map(userMapper::toUserDTO);
    }
}
