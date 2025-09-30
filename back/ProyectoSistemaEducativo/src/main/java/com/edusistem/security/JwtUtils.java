package com.edusistem.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    private final Key jwtSecretKey;
    private final long jwtAccessTokenExpirationMs;
    private final long jwtRefreshTokenExpirationMs;

    public JwtUtils(
            @Value("${jwt.secret}") String jwtSecret,
            @Value("${jwt.access-token-expiration}") long jwtAccessTokenExpirationMs,
            @Value("${jwt.refresh-token-expiration}") long jwtRefreshTokenExpirationMs) {
        this.jwtSecretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        this.jwtAccessTokenExpirationMs = jwtAccessTokenExpirationMs;
        this.jwtRefreshTokenExpirationMs = jwtRefreshTokenExpirationMs;
    }

    public String generateAccessToken(String username) {
        return buildToken(username, jwtAccessTokenExpirationMs);
    }

    public String generateRefreshToken(String username) {
        return buildToken(username, jwtRefreshTokenExpirationMs);
    }

    private String buildToken(String username, long expiration) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + expiration))
                .signWith(jwtSecretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromJwt(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Error validando JWT: " + e.getMessage());
        }
        return false;
    }
}
