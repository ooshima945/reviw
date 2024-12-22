package com.example.demo.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.dto.JournalSumDTO;

class AccountBalanceRowMapper implements RowMapper<JournalSumDTO> {
    @Override
    public JournalSumDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new JournalSumDTO(
                rs.getLong("debit_account_id"),
                rs.getBigDecimal("debit_sum"),
                rs.getLong("credit_account_id"),
                rs.getBigDecimal("credit_sum")
        );
    }
}
