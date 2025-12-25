package com.example.demo.service.impl;

import com.example.demo.entity.LoanRequest;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.LoanRequestRepository;
import com.example.demo.repository.UserRepository;

import java.time.Instant;
import java.util.List;

public class LoanRequestServiceImpl {

    private final LoanRequestRepository loanRequestRepository;
    private final UserRepository userRepository;

    // ✅ Constructor Injection
    public LoanRequestServiceImpl(
            LoanRequestRepository loanRequestRepository,
            UserRepository userRepository) {
        this.loanRequestRepository = loanRequestRepository;
        this.userRepository = userRepository;
    }

    public LoanRequest submitRequest(LoanRequest request) {

        if (request.getRequestedAmount() == null ||
                request.getRequestedAmount() <= 0) {
            throw new BadRequestException("Requested amount");
        }

        Long userId = request.getUser().getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        request.setUser(user);
        request.setStatus(LoanRequest.Status.PENDING.name());
        request.setSubmittedAt(Instant.now());

        return loanRequestRepository.save(request);
    }

    public LoanRequest getById(Long id) {
        return loanRequestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
    }

    public List<LoanRequest> getRequestsByUser(Long userId) {
        return loanRequestRepository.findByUserId(userId);
    }

    public List<LoanRequest> getAllRequests() {
        return loanRequestRepository.findAll();
    }
}
