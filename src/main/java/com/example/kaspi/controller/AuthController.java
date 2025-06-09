// src/main/java/com/example/kaspi/controller/AuthController.java
package com.example.kaspi.controller;

import com.example.kaspi.dto.AuthRequestDto;
import com.example.kaspi.dto.CreateUserDto;
import com.example.kaspi.dto.TokenResponseDto;


import com.example.kaspi.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@Valid @RequestBody AuthRequestDto req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDto> refresh(@Valid @RequestBody Map<String,String> body) {
        String rt = body.get("refreshToken");
        return ResponseEntity.ok(authService.refresh(rt));
    }
    @PostMapping("/register")
    public ResponseEntity<TokenResponseDto> register(@Valid @RequestBody CreateUserDto createUserDto){

        return ResponseEntity.ok(authService.registerUser(createUserDto));
    }


}
