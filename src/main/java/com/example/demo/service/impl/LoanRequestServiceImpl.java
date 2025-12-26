package com.example.demo.service.impl;

import com.example.demo.entity.LoanRequest;
import com.example.demo.entity.User;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.LoanRequestRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LoanRequestServiceImpl {

    private final LoanRequestRepository loanRequestRepository;
    private final UserRepository userRepository;

    // Constructor injection (REQUIRED)
    public LoanRequestServiceImpl(LoanRequestRepository loanRequestRepository,
                                  UserRepository userRepository) {
        this.loanRequestRepository = loanRequestRepository;
        this.userRepository = userRepository;
    }

    // Submit loan request
    public LoanRequest submitRequest(LoanRequest loanRequest) {

        if (loanRequest.getRequestedAmount() == null ||
                loanRequest.getRequestedAmount() <= 0) {
            throw new BadRequestException("Requested amount");
        }

        Long userId = loanRequest.getUser().getId();

        // Check if user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        loanRequest.setUser(user);

        // Status & submittedAt are set automatically by entity
        return loanRequestRepository.save(loanRequest);
    }

    // Get all loan requests for a user
    public List<LoanRequest> getRequestsByUser(Long userId) {
        return loanRequestRepository.findByUserId(userId);
    }

    // Get loan request by id
    public LoanRequest getById(Long loanRequestId) {
        return loanRequestRepository.findById(loanRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
    }
}
