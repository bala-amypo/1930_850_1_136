@Entity
public class EligibilityResult {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private LoanRequest loanRequest;

    private Boolean isEligible;
    private Double maxEligibleAmount;
    private Double estimatedEmi;
    private String riskLevel;
    private String rejectionReason;

    private Timestamp calculatedAt = new Timestamp(System.currentTimeMillis());
}
