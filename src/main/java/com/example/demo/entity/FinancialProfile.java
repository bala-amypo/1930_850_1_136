@Entity
public class FinancialProfile {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private User user;

    private Double monthlyIncome;
    private Double monthlyExpenses;
    private Double existingLoanEmi;
    private Integer creditScore;
    private Double savingsBalance;

    private Timestamp lastUpdatedAt;

    @PrePersist
    @PreUpdate
    void updateTimestamp() {
        lastUpdatedAt = new Timestamp(System.currentTimeMillis());
    }
}
