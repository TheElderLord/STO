package com.example.kaspi.service.auth;

import com.example.kaspi.controller.AuthController;
import com.example.kaspi.domain.UserModel;
import com.example.kaspi.dto.AuthRequestDto;
import com.example.kaspi.dto.CreateUserDto;
import com.example.kaspi.dto.TokenResponseDto;
import com.example.kaspi.enums.RoleEnum;
import com.example.kaspi.repository.UserRepository;
import com.example.kaspi.security.JwtTokenProvider;
import com.example.kaspi.service.refreshToken.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;
    private final JwtTokenProvider jwtProvider;
    private final RefreshTokenService refreshService;
    private final AuthenticationManager authManager;
    private final UserRepository userRepo;
    @Override
    public TokenResponseDto registerUser(CreateUserDto createUserDto) {
        UserModel userModel = new UserModel();
        userModel.setUsername(createUserDto.getUsername());
        userModel.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        userModel.setRole(createUserDto.getRole());
        userRepository.save(userModel);
        String accessToken = jwtProvider.createToken(
                userModel.getUsername(),
                RoleEnum.valueOf(userModel.getRole().name())
        );
        var refreshToken = refreshService.createRefreshToken(userModel);

        return new TokenResponseDto(accessToken, refreshToken.getToken().toString());

    }

    @Override
    public TokenResponseDto login(AuthRequestDto req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );

        UserModel user = userRepo.findByUsername(req.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String accessToken = jwtProvider.createToken(
                user.getUsername(),
                RoleEnum.valueOf(user.getRole().name())
        );
        var refreshToken = refreshService.createRefreshToken(user);

        return new TokenResponseDto(accessToken, refreshToken.getToken().toString());
    }

    @Override
    public TokenResponseDto refresh(String refreshToken) {
        UserModel user = refreshService.verifyAndGetUser(UUID.fromString(refreshToken));

        // 2) Генерим новый access (refresh оставляем тот же или ротируем по необходимости)
        String access = jwtProvider.createToken(user.getUsername(), RoleEnum.valueOf(user.getRole().name()));
        return new TokenResponseDto(access, refreshToken);
    }

}
