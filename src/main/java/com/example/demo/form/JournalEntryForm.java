package com.example.demo.form;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.AssertTrue;
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

    private Integer entryId; 
    // 日付の範囲をチェックするバリデーション
    @AssertTrue(message = "日付は2024年1月1日から2024年12月31日までの範囲で入力してください")
    public boolean isValidDate() {
        if (date == null) {
            return true; // NotNullでチェックするので、nullの場合はここではチェックしない
        }
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
}
