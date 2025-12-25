package com.example.demo.controller;

import com.example.demo.entity.RiskAssessment;
import com.example.demo.service.RiskAssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/risk-logs")
public class RiskLogController {

    @Autowired(required = false)
    private RiskAssessmentService service;

    @PostMapping("/assess/{loanRequestId}")
    public RiskAssessment assess(@PathVariable Long loanRequestId) {
        if (service == null) return null;
        return service.assessRisk(loanRequestId);
    }

    @GetMapping("/{loanRequestId}")
    public RiskAssessment get(@PathVariable Long loanRequestId) {
        if (service == null) return null;
        return service.getByLoanRequestId(loanRequestId);
    }
}
