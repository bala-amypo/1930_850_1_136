package com.example.demo.repository;

import com.example.demo.entity.EligibilityResult;
import java.util.Optional;

public interface EligibilityResultRepository {
    Optional<EligibilityResult> findByLoanRequestId(Long loanRequestId);
    EligibilityResult save(EligibilityResult result);
}
