package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.RiskAssessmentLog;

public interface RiskAssessmentLogRepository extends JpaRepository<RiskAssessmentLog, Long> {

    List<RiskAssessmentLog> findByLoanRequestId(Long loanRequestId);
}
