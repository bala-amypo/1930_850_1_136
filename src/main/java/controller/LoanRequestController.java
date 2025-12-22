package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.LoanRequest;
import com.example.demo.service.LoanRequestService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/loan-requests")
@Tag(name = "Loan Requests", description = "Loan request management operations")
public class LoanRequestController {

    private final LoanRequestService service;

    public LoanRequestController(LoanRequestService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Submit a new loan request")
    public LoanRequest submit(@RequestBody LoanRequest request) {
        return service.submitLoanRequest(request);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get loan requests by user ID")
    public List<LoanRequest> getByUser(@PathVariable Long userId) {
        return service.getRequestsByUser(userId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get loan request by ID")
    public LoanRequest getById(@PathVariable Long id) {
        return service.getRequestById(id);
    }

    @GetMapping
    @Operation(summary = "Get all loan requests")
    public List<LoanRequest> getAll() {
        return service.getAllRequests();
    }
}
