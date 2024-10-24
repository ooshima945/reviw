package com.example.demo.form;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
@Data
public class JournalEntryForm {

    // 日付 (date)
    @NotNull(message = "日付を入力してください")
    private LocalDate date;

    // 借方勘定科目 (debitAccount)
    @NotEmpty(message = "借方勘定科目を入力してください")
    private String debitAccount;

    // 貸方勘定科目 (creditAccount)
    @NotEmpty(message = "貸方勘定科目を入力してください")
    private String creditAccount;

    // 合計金額 (totalAmount)
    @NotNull(message = "合計金額を入力してください")
    private BigDecimal totalAmount;

    // 摘要 (description)
    private String description;


  
}
