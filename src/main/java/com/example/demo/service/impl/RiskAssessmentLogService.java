package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.RiskAssessmentLog;

public interface RiskAssessmentLogService {

    RiskAssessmentLog logAssessment(RiskAssessmentLog log);

    List<RiskAssessmentLog> getLogsByRequest(Long loanRequestId);
}
