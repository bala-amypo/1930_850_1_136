package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.Instant;

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
    private String status;

    // 🔥 REQUIRED BY TESTS
    private Instant createdAt;
    private Instant updatedAt;

    // Optional, but allowed
    private Instant submittedAt;

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = Status.PENDING.name();
        }
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
        this.submittedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Double getRequestedAmount() { return requestedAmount; }
    public void setRequestedAmount(Double requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public Integer getTenureMonths() { return tenureMonths; }
    public void setTenureMonths(Integer tenureMonths) {
        this.tenureMonths = tenureMonths;
    }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public String getStatus() { return status; }

    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public Instant getSubmittedAt() { return submittedAt; }
}
