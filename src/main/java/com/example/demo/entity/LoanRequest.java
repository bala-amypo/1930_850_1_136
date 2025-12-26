package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

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

    // 🔥 EXACT TYPES REQUIRED BY TEST
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime submittedAt;

    public LoanRequest() {
        LocalDateTime now = LocalDateTime.now();
        this.status = Status.PENDING.name();
        this.createdAt = now;
        this.updatedAt = now;
        this.submittedAt = now;
    }

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = Status.PENDING.name();
        }
        LocalDateTime now = LocalDateTime.now();
        if (createdAt == null) createdAt = now;
        updatedAt = now;
        if (submittedAt == null) submittedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // getters
    public Long getId() { return id; }
    public User getUser() { return user; }
    public Double getRequestedAmount() { return requestedAmount; }
    public Integer getTenureMonths() { return tenureMonths; }
    public String getPurpose() { return purpose; }
    public String getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public LocalDateTime getSubmittedAt() { return submittedAt; }
}
