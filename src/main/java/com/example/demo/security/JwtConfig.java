package com.example.demo.config;

import com.example.demo.security.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    public JwtUtil jwtUtil() {
        // Same values used in tests
        return new JwtUtil(
                "ChangeThisSecretForProductionButKeepItLongEnough",
                3600000
        );
    }
}
