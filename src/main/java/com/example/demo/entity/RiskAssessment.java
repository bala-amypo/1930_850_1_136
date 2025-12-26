@Entity
public class RiskAssessment {

    @Id
    @GeneratedValue
    private Long id;

    private Long loanRequestId;
    private Double dtiRatio;
    private Double riskScore;
    private String creditCheckStatus;

    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());
}
