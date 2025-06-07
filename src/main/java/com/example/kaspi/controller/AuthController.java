package com.example.kaspi.controller;

import com.example.kaspi.domain.UserModel;
import com.example.kaspi.dto.CreateUserDto;
import com.example.kaspi.enums.RoleEnum;
import com.example.kaspi.repository.UserRepository;
import com.example.kaspi.security.JwtTokenProvider;
import com.example.kaspi.service.users.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {
        // 1) аутентификация по username/password
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );

        // 2) достаём из БД модель пользователя, чтобы получить единственную RoleEnum
        UserModel user = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + auth.getName()));

        RoleEnum role = user.getRole();

        // 3) генерируем JWT, передавая единичную роль
        String token = tokenProvider.createToken(user.getUsername(), role);

        return ResponseEntity.ok(Map.of("token", token));
    }
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody CreateUserDto createUserDto){

        return ResponseEntity.ok(userService.registerUser(createUserDto));
    }

    @Data
    static class AuthRequest {
        private String username;
        private String password;
    }
}
