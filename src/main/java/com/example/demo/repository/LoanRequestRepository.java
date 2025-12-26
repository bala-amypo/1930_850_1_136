package com.example.demo.repository;

import com.example.demo.entity.LoanRequest;
import java.util.*;

public interface LoanRequestRepository {
    Optional<LoanRequest> findById(Long id);
    List<LoanRequest> findByUserId(Long userId);
    List<LoanRequest> findAll();
    LoanRequest save(LoanRequest request);
}
