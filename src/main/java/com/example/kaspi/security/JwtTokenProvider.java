package com.example.kaspi.security;


import com.example.kaspi.enums.RoleEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    private final SecretKey secretKey;
    private final long validityMillis;

    public JwtTokenProvider(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.expiration}") long expirationMillis
    ) {
        this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
        this.validityMillis = expirationMillis;
    }

    public String createToken(String username, RoleEnum role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", role);

        Date now = new Date();
        Date exp = new Date(now.getTime() + validityMillis);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }


}

