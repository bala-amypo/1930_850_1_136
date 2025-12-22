package com.example.demo.dto;

public class LoanDtos {

    public static class LoanRequestDto {
        public Double requestedAmount;
        public Integer tenureMonths;
        public String purpose;
    }

    public static class FinancialProfileDto {
        public Double monthlyIncome;
        public Double monthlyExpenses;
        public Double existingLoanEmi;
        public Integer creditScore;
        public Double savingsBalance;
    }
}
