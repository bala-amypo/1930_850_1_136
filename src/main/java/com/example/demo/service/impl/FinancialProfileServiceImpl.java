package com.example.demo.service.impl;

import com.example.demo.entity.FinancialProfile;
import com.example.demo.exception.BadRequestException;
import com.example.demo.repository.FinancialProfileRepository;
import com.example.demo.service.FinancialProfileService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class FinancialProfileServiceImpl implements FinancialProfileService {

    private final FinancialProfileRepository repository;

    public FinancialProfileServiceImpl(FinancialProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public FinancialProfile createOrUpdateProfile(FinancialProfile profile) {

        FinancialProfile existing =
                repository.findByUserId(profile.getUser().getId());

        if (existing != null && profile.getId() == null) {
            throw new BadRequestException("Financial profile already exists");
        }

        profile.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
        return repository.save(profile);
    }

    @Override
    public FinancialProfile getProfileByUser(Long userId) {
        return repository.findByUserId(userId);
    }
}
