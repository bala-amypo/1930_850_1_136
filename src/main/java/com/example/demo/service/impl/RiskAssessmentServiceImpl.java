package com.example.demo.service.impl;

import com.example.demo.entity.FinancialProfile;
import com.example.demo.entity.LoanRequest;
import com.example.demo.entity.RiskAssessment;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.FinancialProfileRepository;
import com.example.demo.repository.LoanRequestRepository;
import com.example.demo.repository.RiskAssessmentRepository;
import com.example.demo.service.RiskAssessmentService;

public class RiskAssessmentServiceImpl implements RiskAssessmentService {

    private final LoanRequestRepository loanRequestRepository;
    private final FinancialProfileRepository financialProfileRepository;
    private final RiskAssessmentRepository riskAssessmentRepository;

    // ✅ Constructor Injection
    public RiskAssessmentServiceImpl(
            LoanRequestRepository loanRequestRepository,
            FinancialProfileRepository financialProfileRepository,
            RiskAssessmentRepository riskAssessmentRepository) {

        this.loanRequestRepository = loanRequestRepository;
        this.financialProfileRepository = financialProfileRepository;
        this.riskAssessmentRepository = riskAssessmentRepository;
    }

    @Override
    public RiskAssessment assessRisk(Long loanRequestId) {

        if (riskAssessmentRepository.findByLoanRequestId(loanRequestId).isPresent()) {
            throw new BadRequestException("Duplicate risk");
        }

        LoanRequest loanRequest = loanRequestRepository.findById(loanRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        FinancialProfile profile =
                financialProfileRepository.findByUserId(loanRequest.getUser().getId())
                        .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));

        double income = profile.getMonthlyIncome() == null ? 0 : profile.getMonthlyIncome();
        double obligations =
                (profile.getMonthlyExpenses() == null ? 0 : profile.getMonthlyExpenses()) +
                (profile.getExistingLoanEmi() == null ? 0 : profile.getExistingLoanEmi());

        double dti = income == 0 ? 0 : obligations / income;

        RiskAssessment risk = new RiskAssessment();
        risk.setLoanRequestId(loanRequestId);
        risk.setDtiRatio(dti);
        risk.setCreditCheckStatus(profile.getCreditScore() >= 600 ? "PASS" : "FAIL");

        return riskAssessmentRepository.save(risk);
    }

    @Override
    public RiskAssessment getByLoanRequestId(Long loanRequestId) {
        return riskAssessmentRepository.findByLoanRequestId(loanRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
    }
}
