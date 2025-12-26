@Entity
public class LoanRequest {

    public enum Status { PENDING, APPROVED, REJECTED }

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    private Double requestedAmount;
    private Integer tenureMonths;
    private String purpose;

    private String status = Status.PENDING.name();

    private Timestamp submittedAt;

    @PrePersist
    void onCreate() {
        submittedAt = new Timestamp(System.currentTimeMillis());
    }
}
