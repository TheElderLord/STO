package com.example.kaspi.service.auth;

import com.example.kaspi.controller.AuthController;
import com.example.kaspi.dto.AuthRequestDto;
import com.example.kaspi.dto.CreateUserDto;
import com.example.kaspi.dto.TokenResponseDto;

public interface AuthService {
    TokenResponseDto registerUser(CreateUserDto createUserDto);
    TokenResponseDto login(AuthRequestDto req);
    TokenResponseDto refresh(String refreshToken);
}
