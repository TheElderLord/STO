package com.example.kaspi.repository;


import com.example.kaspi.domain.RefreshToken;
import com.example.kaspi.domain.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findByToken(UUID token);
    void deleteByUser(UserModel user);
}

