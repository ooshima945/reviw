package com.example.demo.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class JournalSumDTO {
    private Long debitAccountId;
    private BigDecimal debitSum;
    private Long creditAccountId;
    private BigDecimal creditSum;

    
    public JournalSumDTO(Long debitAccountId, BigDecimal debitSum, Long creditAccountId, BigDecimal creditSum) {
        this.debitAccountId = debitAccountId;
        this.debitSum = debitSum;
        this.creditAccountId = creditAccountId;
        this.creditSum = creditSum;
    }
}
