package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

public class UserServiceImpl {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // ✅ Constructor Injection (MANDATORY)
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Used in tests: registerUser / register
    public User register(User user) {

        Optional<User> existing = userRepository.findByEmail(user.getEmail());
        if (existing.isPresent()) {
            throw new BadRequestException("Email already in use");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(User.Role.CUSTOMER.name());

        return userRepository.save(user);
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
