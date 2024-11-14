package com.xocialive.accubook.controller;

import com.xocialive.accubook.annotation.CheckUserAuthorization;
import com.xocialive.accubook.model.dto.user.UserCreateDTO;
import com.xocialive.accubook.model.dto.user.UserDTO;
import com.xocialive.accubook.model.entity.UserPrincipal;
import com.xocialive.accubook.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping("")
    public ResponseEntity<String> update(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody UserCreateDTO userCreateDTO) {
        Long userId = userPrincipal.getId();
        userService.update(userId, userCreateDTO);
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
