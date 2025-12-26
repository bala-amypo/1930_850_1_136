package com.example.demo.service.impl;

import com.example.demo.entity.FinancialProfile;
import com.example.demo.entity.LoanRequest;
import com.example.demo.entity.RiskAssessment;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.FinancialProfileRepository;
import com.example.demo.repository.LoanRequestRepository;
import com.example.demo.repository.RiskAssessmentRepository;

public class RiskAssessmentServiceImpl {

    private final LoanRequestRepository loanRequestRepository;
    private final FinancialProfileRepository financialProfileRepository;
    private final RiskAssessmentRepository riskAssessmentRepository;

    // Constructor injection (REQUIRED)
    public RiskAssessmentServiceImpl(LoanRequestRepository loanRequestRepository,
                                     FinancialProfileRepository financialProfileRepository,
                                     RiskAssessmentRepository riskAssessmentRepository) {
        this.loanRequestRepository = loanRequestRepository;
        this.financialProfileRepository = financialProfileRepository;
        this.riskAssessmentRepository = riskAssessmentRepository;
    }

    // Assess loan risk
    public RiskAssessment assessRisk(Long loanRequestId) {

        // Prevent duplicate risk assessment
        if (riskAssessmentRepository.findByLoanRequestId(loanRequestId).isPresent()) {
            throw new BadRequestException("Duplicate risk");
        }

        LoanRequest loanRequest = loanRequestRepository.findById(loanRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        FinancialProfile profile = financialProfileRepository
                .findByUserId(loanRequest.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        RiskAssessment assessment = new RiskAssessment();
        assessment.setLoanRequestId(loanRequestId);

        double income = profile.getMonthlyIncome() == null ? 0.0 : profile.getMonthlyIncome();
        double expenses = profile.getMonthlyExpenses() == null ? 0.0 : profile.getMonthlyExpenses();
        double existingEmi = profile.getExistingLoanEmi() == null ? 0.0 : profile.getExistingLoanEmi();

        double dti = income == 0 ? 0.0 : (expenses + existingEmi) / income;
        assessment.setDtiRatio(dti);

        double riskScore = Math.min(100.0, dti * 100);
        assessment.setRiskScore(riskScore);

        return riskAssessmentRepository.save(assessment);
    }

    // Get risk assessment by loan request id
    public RiskAssessment getByLoanRequestId(Long loanRequestId) {
        return riskAssessmentRepository.findByLoanRequestId(loanRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
    }
}
