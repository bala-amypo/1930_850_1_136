public class LoanRequestServiceImpl {

    private final LoanRequestRepository repo;
    private final UserRepository userRepo;

    public LoanRequestServiceImpl(LoanRequestRepository r, UserRepository u) {
        this.repo = r;
        this.userRepo = u;
    }

    public LoanRequest submitRequest(LoanRequest lr) {
        if (lr.getRequestedAmount() <= 0)
            throw new BadRequestException("Requested amount");

        userRepo.findById(lr.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return repo.save(lr);
    }

    public List<LoanRequest> getRequestsByUser(Long id) {
        return repo.findByUserId(id);
    }

    public LoanRequest getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
    }
}
