package com.example.demo.form;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

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

    // Getter & Setter methods

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDebitAccount() {
        return debitAccount;
    }

    public void setDebitAccount(String debitAccount) {
        this.debitAccount = debitAccount;
    }

    public String getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(String creditAccount) {
        this.creditAccount = creditAccount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
