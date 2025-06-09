package com.example.kaspi.service.refreshToken;


import com.example.kaspi.domain.RefreshToken;
import com.example.kaspi.domain.UserModel;
import com.example.kaspi.repository.RefreshTokenRepository;
import com.example.kaspi.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${security.jwt.refresh-expiration:604800000}") // по умолчанию 7 дней
    private long refreshExpirationMs;

    private final RefreshTokenRepository refreshRepo;
    private final UserRepository userRepo;

    @Override
    public RefreshToken createRefreshToken(UserModel user) {
        // удаляем старые токены
        refreshRepo.deleteByUser(user);

        RefreshToken rt = RefreshToken.builder()
                .token(UUID.randomUUID())
                .user(user)
                .expiresAt(Instant.now().plusMillis(refreshExpirationMs))
                .build();

        return refreshRepo.save(rt);
    }

    @Override
    public UserModel verifyAndGetUser(UUID token) {
        RefreshToken rt = refreshRepo.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

        if (rt.getExpiresAt().isBefore(Instant.now())) {
            refreshRepo.delete(rt);
            throw new IllegalArgumentException("Refresh token expired");
        }
        return rt.getUser();
    }

    @Override
    public void deleteByUser(UserModel user) {
        refreshRepo.deleteByUser(user);
    }
}

