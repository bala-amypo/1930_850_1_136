package com.example.demo.security;

import io.jsonwebtoken.*;
import java.util.*;

public class JwtUtil {

    private final String secret;
    private final long validity;

    public JwtUtil(String secret, long validityInMs) {
        this.secret = secret;
        this.validity = validityInMs;
    }

    public String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
