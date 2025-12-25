package com.example.demo.controller;

import com.example.demo.entity.LoanRequest;
import com.example.demo.service.LoanRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan-requests")
public class LoanRequestController {

    @Autowired(required = false)
    private LoanRequestService service;

    @PostMapping
    public LoanRequest submit(@RequestBody LoanRequest request) {
        if (service == null) return null;
        return service.submitRequest(request);
    }

    @GetMapping("/user/{userId}")
    public List<LoanRequest> getByUser(@PathVariable Long userId) {
        if (service == null) return null;
        return service.getRequestsByUser(userId);
    }

    @GetMapping("/{id}")
    public LoanRequest getById(@PathVariable Long id) {
        if (service == null) return null;
        return service.getById(id);
    }

    @GetMapping
    public List<LoanRequest> getAll() {
        if (service == null) return null;
        return service.getAllRequests();
    }
}
