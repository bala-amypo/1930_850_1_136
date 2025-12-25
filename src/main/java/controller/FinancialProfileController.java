package com.example.demo.controller;

import com.example.demo.entity.FinancialProfile;
import com.example.demo.service.FinancialProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/financial-profiles")
public class FinancialProfileController {

    @Autowired(required = false)
    private FinancialProfileService service;

    @PostMapping
    public FinancialProfile createOrUpdate(@RequestBody FinancialProfile profile) {
        if (service == null) return null;
        return service.createOrUpdate(profile);
    }

    @GetMapping("/user/{userId}")
    public FinancialProfile getByUser(@PathVariable Long userId) {
        if (service == null) return null;
        return service.getByUserId(userId);
    }
}
