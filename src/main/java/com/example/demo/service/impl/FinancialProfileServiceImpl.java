package com.example.demo.service.impl;

import com.example.demo.entity.FinancialProfile;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.FinancialProfileRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class FinancialProfileServiceImpl {

    private final FinancialProfileRepository financialProfileRepository;
    private final UserRepository userRepository;

    // Constructor injection (REQUIRED by tests)
    public FinancialProfileServiceImpl(FinancialProfileRepository financialProfileRepository,
                                       UserRepository userRepository) {
        this.financialProfileRepository = financialProfileRepository;
        this.userRepository = userRepository;
    }

    // Create or update financial profile
    public FinancialProfile createOrUpdate(FinancialProfile profile) {

        if (profile.getCreditScore() == null ||
                profile.getCreditScore() < 300 ||
                profile.getCreditScore() > 900) {
            throw new BadRequestException("Invalid credit score");
        }

        Long userId = profile.getUser().getId();

        // Check if user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        profile.setUser(user);

        // Check if profile already exists
        Optional<FinancialProfile> existing =
                financialProfileRepository.findByUserId(userId);

        if (existing.isPresent()) {
            // Update existing profile instead of creating new
            profile.setId(existing.get().getId());
        }

        return financialProfileRepository.save(profile);
    }

    // Get profile by user id
    public FinancialProfile getByUserId(Long userId) {
        return financialProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
