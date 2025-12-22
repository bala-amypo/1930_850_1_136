package com.example.demo.entity;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RiskAssessmentLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long loanRequestId;

    private Double dtiRatio;
    private String creditCheckStatus;

    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    // ===== Getters & Setters =====

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

    public String getCreditCheckStatus() {
        return creditCheckStatus;
    }
 
    public void setCreditCheckStatus(String creditCheckStatus) {
        this.creditCheckStatus = creditCheckStatus;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
 
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
