package com.example.demo.repository;

import com.example.demo.entity.RiskAssessment;
import java.util.Optional;

public interface RiskAssessmentRepository {
    Optional<RiskAssessment> findByLoanRequestId(Long loanRequestId);
    RiskAssessment save(RiskAssessment risk);
}
