public class RiskAssessmentServiceImpl {

    private final LoanRequestRepository loanRepo;
    private final FinancialProfileRepository profileRepo;
    private final RiskAssessmentRepository repo;

    public RiskAssessmentServiceImpl(LoanRequestRepository l,
                                     FinancialProfileRepository f,
                                     RiskAssessmentRepository r) {
        loanRepo = l;
        profileRepo = f;
        repo = r;
    }

    public RiskAssessment assessRisk(Long requestId) {

        if (repo.findByLoanRequestId(requestId).isPresent())
            throw new BadRequestException("Duplicate risk");

        LoanRequest lr = loanRepo.findById(requestId).orElseThrow();
        FinancialProfile fp = profileRepo.findByUserId(lr.getUser().getId()).orElseThrow();

        RiskAssessment ra = new RiskAssessment();
        ra.setLoanRequestId(requestId);

        double income = fp.getMonthlyIncome();
        ra.setDtiRatio(income == 0 ? 0.0 :
                (fp.getMonthlyExpenses() + fp.getExistingLoanEmi()) / income);

        ra.setRiskScore(Math.min(100, ra.getDtiRatio() * 100));

        return repo.save(ra);
    }

    public RiskAssessment getByLoanRequestId(Long id) {
        return repo.findByLoanRequestId(id).orElseThrow();
    }
}
