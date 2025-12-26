package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.Map;

public class JwtUtil {

    private final String secret;
    private final long jwtExpirationInMs;

    // Constructor used in tests
    public JwtUtil(String secret, long jwtExpirationInMs) {
        this.secret = secret;
        this.jwtExpirationInMs = jwtExpirationInMs;
    }

    // Generate JWT token
    public String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    // Get all claims from token
    public Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
