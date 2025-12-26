public class FinancialProfileServiceImpl {

    private final FinancialProfileRepository repo;
    private final UserRepository userRepo;

    public FinancialProfileServiceImpl(FinancialProfileRepository r, UserRepository u) {
        this.repo = r;
        this.userRepo = u;
    }

    public FinancialProfile createOrUpdate(FinancialProfile fp) {

        if (fp.getCreditScore() < 300 || fp.getCreditScore() > 900)
            throw new BadRequestException("Invalid credit score");

        Long userId = fp.getUser().getId();
        userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Optional<FinancialProfile> existing = repo.findByUserId(userId);
        if (existing.isPresent()) {
            fp.setId(existing.get().getId());
        }
        return repo.save(fp);
    }

    public FinancialProfile getByUserId(Long id) {
        return repo.findByUserId(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
