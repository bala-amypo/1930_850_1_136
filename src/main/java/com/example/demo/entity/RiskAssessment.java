package com.example.demo.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
public class RiskAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long loanRequestId;

    private Double dtiRatio;
    private Double riskScore;
    private String creditCheckStatus;

    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    // ===== GETTERS & SETTERS =====

    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }

    public Long getLoanRequestId() {
        return loanRequestId;
    }
 
    public void setLoanRequestId(Long loanRequestId) {
        this.loanRequestId = loanRequestId;
    }

    public Double getDtiRatio() {
        return dtiRatio;
    }
 
    public void setDtiRatio(Double dtiRatio) {
        this.dtiRatio = dtiRatio;
    }

    public Double getRiskScore() {
        return riskScore;
    }
 
    public void setRiskScore(Double riskScore) {
        this.riskScore = riskScore;
    }

    public String getCreditCheckStatus() {
        return creditCheckStatus;
    }
 
    public void setCreditCheckStatus(String creditCheckStatus) {
        this.creditCheckStatus = creditCheckStatus;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
