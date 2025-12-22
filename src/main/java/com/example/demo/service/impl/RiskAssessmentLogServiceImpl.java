package com.example.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.RiskAssessmentLog;
import com.example.demo.repository.RiskAssessmentLogRepository;
import com.example.demo.service.RiskAssessmentLogService;

@Service
public class RiskAssessmentLogServiceImpl implements RiskAssessmentLogService {

    private final RiskAssessmentLogRepository repository;

    public RiskAssessmentLogServiceImpl(RiskAssessmentLogRepository repository) {
        this.repository = repository;
    }

    @Override
    public RiskAssessmentLog logAssessment(RiskAssessmentLog log) {
        return repository.save(log);
    }

    @Override
    public List<RiskAssessmentLog> getLogsByRequest(Long loanRequestId) {
        return repository.findByLoanRequestId(loanRequestId);
    }
}
