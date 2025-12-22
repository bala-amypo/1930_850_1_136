package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.FinancialProfile;
import com.example.demo.service.FinancialProfileService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/financial-profiles")
@Tag(name = "Financial Profiles", description = "Financial profile management operations")
public class FinancialProfileController {

    private final FinancialProfileService service;

    public FinancialProfileController(FinancialProfileService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create or update financial profile")
    public FinancialProfile createOrUpdate(@RequestBody FinancialProfile profile) {
        return service.createOrUpdateProfile(profile);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get financial profile by user ID")
    public FinancialProfile getByUser(@PathVariable Long userId) {
        return service.getProfileByUser(userId);
    }
}
