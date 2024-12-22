package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.JournalSumDTO;
import com.example.demo.entity.JournalDetail;
@Repository
public interface JournalDetailRepository extends JpaRepository<JournalDetail, Integer> {
    @Query(value = "SELECT " +
            "jd.debit_account_id, SUM(jd.debit_amount) AS debit_sum, jd.credit_account_id, SUM(jd.credit_amount) AS credit_sum " +
            "FROM journal_details jd " +
            "INNER JOIN journal_entries je ON jd.entry_id = je.entry_id " +
            "INNER JOIN general_users gu ON je.general_id = gu.general_id " +
            "WHERE gu.admin_id = :adminId " +
            "GROUP BY jd.debit_account_id, jd.credit_account_id", nativeQuery = true)
    List<JournalSumDTO> findJournalSumsByAdminId(@Param("adminId") long adminId);
}



