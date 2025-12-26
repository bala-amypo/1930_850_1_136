public class EligibilityServiceImpl {

    private final LoanRequestRepository loanRepo;
    private final FinancialProfileRepository profileRepo;
    private final EligibilityResultRepository resultRepo;

    public EligibilityServiceImpl(LoanRequestRepository l,
                                  FinancialProfileRepository f,
                                  EligibilityResultRepository e) {
        loanRepo = l;
        profileRepo = f;
        resultRepo = e;
    }

    public EligibilityResult evaluateEligibility(Long requestId) {

        if (resultRepo.findByLoanRequestId(requestId).isPresent())
            throw new BadRequestException("Financial profile already exists");

        LoanRequest lr = loanRepo.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        FinancialProfile fp = profileRepo.findByUserId(lr.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));

        EligibilityResult er = new EligibilityResult();
        er.setLoanRequest(lr);
        er.setMaxEligibleAmount(fp.getMonthlyIncome() * 10);
        er.setIsEligible(true);

        return resultRepo.save(er);
    }

    public EligibilityResult getByLoanRequestId(Long id) {
        return resultRepo.findByLoanRequestId(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
    }
}
