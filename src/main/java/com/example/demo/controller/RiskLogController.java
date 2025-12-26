package com.example.demo.controller;

import com.example.demo.entity.RiskAssessment;
import com.example.demo.service.RiskAssessmentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/risk-logs")
public class RiskLogController {

    private final RiskAssessmentService service;

    public RiskLogController(RiskAssessmentService service) {
        this.service = service;
    }

    @PostMapping("/assess/{loanRequestId}")
    public RiskAssessment assess(@PathVariable Long loanRequestId) {
        return service.assessRisk(loanRequestId);
    }

    @GetMapping("/{loanRequestId}")
    public RiskAssessment get(@PathVariable Long loanRequestId) {
        return service.getByLoanRequestId(loanRequestId);
    }
}
