package com.xocialive.accubook.controller;

import com.xocialive.accubook.model.dto.user.UserCreateDTO;
import com.xocialive.accubook.model.dto.user.UserLoginDTO;
import com.xocialive.accubook.security.AuthResponse;
import com.xocialive.accubook.service.AuthService;
import com.xocialive.accubook.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;


    @PostMapping("/login") //Returns JWT if successful
    public ResponseEntity<?> login(@RequestBody UserLoginDTO loginRequest) {
        try {
            String jwt = authService.authenticateAndGenerateToken(loginRequest);
            return ResponseEntity.ok(new AuthResponse(jwt));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserCreateDTO user) {
        userService.create(user);
        return ResponseEntity.ok("User Created Successfully");
    }

}
