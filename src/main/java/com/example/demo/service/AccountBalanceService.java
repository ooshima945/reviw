package com.example.demo.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.example.demo.dto.JournalSumDTO;
import com.example.demo.repository.JournalDetailRepository;
import com.example.demo.util.AccountBalanceCalculator;
@Service
public class AccountBalanceService {

    private final AccountBalanceCalculator accountBalanceCalculator;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public AccountBalanceService(JournalDetailRepository journalDetailRepository, AccountBalanceCalculator accountBalanceCalculator, JdbcTemplate jdbcTemplate) {
        this.accountBalanceCalculator = accountBalanceCalculator;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, Object> getAccountBalances(long adminId) {
        List<JournalSumDTO> journalSums = getJournalSumsByAdminId(adminId);
        if (journalSums == null || journalSums.isEmpty()) {
            return Map.of();
        }

        return accountBalanceCalculator.calculateBalances(journalSums);
    }


    private List<JournalSumDTO> getJournalSumsByAdminId(long adminId) {
        String sql = "SELECT " +
                     "jd.debit_account_id, SUM(jd.debit_amount) AS debit_sum, jd.credit_account_id, SUM(jd.credit_amount) AS credit_sum " +
                     "FROM journal_details jd " +
                     "INNER JOIN journal_entries je ON jd.entry_id = je.entry_id " +
                     "INNER JOIN general_users gu ON je.general_id = gu.general_id " +
                     "WHERE gu.admin_id = ? AND je.deleted_at IS NULL " + // deleted_at が null である条件を追加
                     "GROUP BY jd.debit_account_id, jd.credit_account_id";
        return jdbcTemplate.query(sql, new JournalSumRowMapper(), adminId);
    }
    private static class JournalSumRowMapper implements RowMapper<JournalSumDTO> {
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
}