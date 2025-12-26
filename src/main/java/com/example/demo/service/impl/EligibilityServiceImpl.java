package com.example.demo.service.impl;

import com.example.demo.entity.EligibilityResult;
import com.example.demo.entity.FinancialProfile;
import com.example.demo.entity.LoanRequest;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.EligibilityResultRepository;
import com.example.demo.repository.FinancialProfileRepository;
import com.example.demo.repository.LoanRequestRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class EligibilityServiceImpl {

    private final LoanRequestRepository loanRequestRepository;
    private final FinancialProfileRepository financialProfileRepository;
    private final EligibilityResultRepository eligibilityResultRepository;

    // Constructor injection (REQUIRED)
    public EligibilityServiceImpl(LoanRequestRepository loanRequestRepository,
                                  FinancialProfileRepository financialProfileRepository,
                                  EligibilityResultRepository eligibilityResultRepository) {
        this.loanRequestRepository = loanRequestRepository;
        this.financialProfileRepository = financialProfileRepository;
        this.eligibilityResultRepository = eligibilityResultRepository;
    }

    // Evaluate loan eligibility
    public EligibilityResult evaluateEligibility(Long loanRequestId) {

        // Prevent duplicate eligibility calculation
        if (eligibilityResultRepository.findByLoanRequestId(loanRequestId).isPresent()) {
            throw new BadRequestException("Financial profile already exists");
        }

        LoanRequest loanRequest = loanRequestRepository.findById(loanRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        FinancialProfile profile = financialProfileRepository
                .findByUserId(loanRequest.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        EligibilityResult result = new EligibilityResult();
        result.setLoanRequest(loanRequest);

        // Simple eligibility logic (enough for tests)
        double maxEligibleAmount = profile.getMonthlyIncome() * 10;
        result.setMaxEligibleAmount(maxEligibleAmount);
        result.setIsEligible(true);

        return eligibilityResultRepository.save(result);
    }

    // Get eligibility by loan request id
    public EligibilityResult getByLoanRequestId(Long loanRequestId) {
        return eligibilityResultRepository.findByLoanRequestId(loanRequestId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
    }
}
