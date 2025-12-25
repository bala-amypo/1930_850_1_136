package com.example.demo.service.impl;

import com.example.demo.entity.FinancialProfile;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.FinancialProfileRepository;
import com.example.demo.repository.UserRepository;

import java.util.Optional;

public class FinancialProfileServiceImpl {

    private final FinancialProfileRepository profileRepository;
    private final UserRepository userRepository;

    // ✅ Constructor Injection
    public FinancialProfileServiceImpl(
            FinancialProfileRepository profileRepository,
            UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    public FinancialProfile createOrUpdate(FinancialProfile profile) {

        if (profile.getCreditScore() == null ||
                profile.getCreditScore() < 300 ||
                profile.getCreditScore() > 900) {
            throw new BadRequestException("Invalid credit score");
        }

        Long userId = profile.getUser().getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Optional<FinancialProfile> existing =
                profileRepository.findByUserId(userId);

        if (existing.isPresent()) {
            // update existing profile instead of creating duplicate
            profile.setId(existing.get().getId());
        }

        profile.setUser(user);
        return profileRepository.save(profile);
    }

    public FinancialProfile getByUserId(Long userId) {
        return profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
