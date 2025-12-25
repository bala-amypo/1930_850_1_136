package com.example.demo.controller;

import com.example.demo.entity.EligibilityResult;
import com.example.demo.service.EligibilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/eligibility")
public class EligibilityController {

    @Autowired(required = false)
    private EligibilityService service;

    @PostMapping("/evaluate/{loanRequestId}")
    public EligibilityResult evaluate(@PathVariable Long loanRequestId) {
        if (service == null) return null;
        return service.evaluateEligibility(loanRequestId);
    }

    @GetMapping("/result/{loanRequestId}")
    public EligibilityResult getResult(@PathVariable Long loanRequestId) {
        if (service == null) return null;
        return service.getByLoanRequestId(loanRequestId);
    }
}
