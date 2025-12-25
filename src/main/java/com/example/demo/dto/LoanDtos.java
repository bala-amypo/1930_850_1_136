package com.example.demo.dto;

public class LoanDtos {

    public static class LoanRequestDto {
        public Double amount;
        public Integer tenureMonths;
    }

    public static class LoanResponseDto {
        public Long loanId;
        public String status;
    }
}
