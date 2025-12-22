package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.RiskAssessmentLog;
import com.example.demo.service.RiskAssessmentLogService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/risk-logs")
@Tag(name = "Risk Logs", description = "Risk assessment log operations")
public class RiskLogController {

    private final RiskAssessmentLogService service;

    public RiskLogController(RiskAssessmentLogService service) {
        this.service = service;
    }

    @GetMapping("/{loanRequestId}")
    @Operation(summary = "Get risk assessment logs by loan request ID")
    public List<RiskAssessmentLog> getLogs(@PathVariable Long loanRequestId) {
        return service.getLogsByRequest(loanRequestId);
    }
}
