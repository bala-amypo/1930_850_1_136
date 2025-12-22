package com.example.demo.service.impl;

import com.example.demo.entity.*;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.*;
import com.example.demo.service.LoanEligibilityService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class LoanEligibilityServiceImpl implements LoanEligibilityService {

    private final LoanRequestRepository loanRequestRepository;
    private final FinancialProfileRepository financialProfileRepository;
    private final EligibilityResultRepository eligibilityResultRepository;
    private final RiskAssessmentLogRepository riskLogRepository;

    public LoanEligibilityServiceImpl(
            LoanRequestRepository loanRequestRepository,
            FinancialProfileRepository financialProfileRepository,
            EligibilityResultRepository eligibilityResultRepository,
            RiskAssessmentLogRepository riskLogRepository) {

        this.loanRequestRepository = loanRequestRepository;
        this.financialProfileRepository = financialProfileRepository;
        this.eligibilityResultRepository = eligibilityResultRepository;
        this.riskLogRepository = riskLogRepository;
    }

    @Override
    public EligibilityResult evaluateEligibility(Long loanRequestId) {

        LoanRequest request = loanRequestRepository.findById(loanRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Requested amount"));

        FinancialProfile profile = financialProfileRepository
                .findByUserId(request.getUser().getId());

        double monthlyIncome = profile.getMonthlyIncome();
        double existingEmi = profile.getExistingLoanEmi() != null
                ? profile.getExistingLoanEmi() : 0;

        // Simple EMI estimation
        double estimatedEmi = request.getRequestedAmount() / request.getTenureMonths();

        double dti = (existingEmi + estimatedEmi) / monthlyIncome;

        String riskLevel;
        boolean eligible = true;
        String rejectionReason = null;

        if (profile.getCreditScore() < 600 || dti > 0.6) {
            eligible = false;
            riskLevel = "HIGH";
            rejectionReason = "High risk applicant";
        } else if (dti > 0.4) {
            riskLevel = "MEDIUM";
        } else {
            riskLevel = "LOW";
        }

        EligibilityResult result = new EligibilityResult();
        result.setLoanRequest(request);
        result.setIsEligible(eligible);
        result.setEstimatedEmi(estimatedEmi);
        result.setRiskLevel(riskLevel);
        result.setCalculatedAt(new Timestamp(System.currentTimeMillis()));

        if (eligible) {
            result.setMaxEligibleAmount(request.getRequestedAmount());
        } else {
            result.setRejectionReason(rejectionReason);
        }

        eligibilityResultRepository.save(result);

        RiskAssessmentLog log = new RiskAssessmentLog();
        log.setLoanRequestId(request.getId());
        log.setDtiRatio(dti);
        log.setCreditCheckStatus(profile.getCreditScore() >= 600 ? "PASS" : "FAIL");
        log.setTimestamp(new Timestamp(System.currentTimeMillis()));

        riskLogRepository.save(log);

        return result;
    }

    @Override
    public EligibilityResult getResultByRequest(Long requestId) {
        return eligibilityResultRepository.findByLoanRequestId(requestId);
    }
}
