package com.xocialive.accubook.controller;

import com.xocialive.accubook.annotation.CheckUserAuthorization;
import com.xocialive.accubook.model.dto.user.UserCreateDTO;
import com.xocialive.accubook.model.dto.user.UserDTO;
import com.xocialive.accubook.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @CheckUserAuthorization
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody UserCreateDTO userCreateDTO) {
        userService.update(id, userCreateDTO);
        return ResponseEntity.ok("User Updated Successfully");
    }

    @CheckUserAuthorization
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<UserDTO> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
