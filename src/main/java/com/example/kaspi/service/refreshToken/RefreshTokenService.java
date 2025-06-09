package com.example.kaspi.service.refreshToken;


import com.example.kaspi.domain.RefreshToken;
import com.example.kaspi.domain.UserModel;
import com.example.kaspi.enums.RoleEnum;

import java.time.Instant;
import java.util.UUID;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(UserModel user);
    UserModel verifyAndGetUser(UUID token);
    void deleteByUser(UserModel user);
}

