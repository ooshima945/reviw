package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.JournalEntry;

public interface JournalEntryRepository extends JpaRepository<JournalEntry, Integer> {
	 List<JournalEntry> findAllByGeneralUserGeneralIdIn(List<Integer> generalUserIds); // generalIdがリスト内にあるエントリを取得

	 List<JournalEntry> findAllByGeneralUserGeneralId(Integer generalId);
	 
	 @Query("""
			 SELECT je.entryId AS entryId, je.entryDate AS entryDate, je.description AS description,
			 jd.debitAccount.accountId AS debitAccountId, jd.debitAmount AS debitAmount,
			 jd.creditAccount.accountId AS creditAccountId, jd.creditAmount AS creditAmount,
			 da.accountName AS debitAccountName, ca.accountName AS creditAccountName
			 FROM JournalEntry je
			 JOIN je.journalDetails jd
			 JOIN jd.debitAccount da
			 JOIN jd.creditAccount ca
			 WHERE je.deletedAt IS NULL AND je.generalUser.generalId IN (
			 SELECT gu.generalId FROM GeneralUser gu WHERE gu.adminUser.adminId = 
			 (SELECT gu2.adminUser.adminId FROM GeneralUser gu2 WHERE gu2.generalId = :generalId)
			 )
			 """)
			 List<Object[]> findEntriesWithDetailsByGeneralId(@Param("generalId") Integer generalId);

	void deleteByEntryId(Integer entryId);
	
	@Query("""
	        SELECT 
	            je.entryId, je.entryDate, je.description,
	            jd.debitAmount, jd.creditAmount,
	            da.accountName, ca.accountName
	        FROM JournalEntry je
	        JOIN je.journalDetails jd
	        JOIN jd.debitAccount da
	        JOIN jd.creditAccount ca
	        WHERE je.generalUser.adminUser.adminId = :adminId
	        """)
	    List<Object[]> findJournalEntriesWithDetailsByAdminId(@Param("adminId") Integer adminId);
}



