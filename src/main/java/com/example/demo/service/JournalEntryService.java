package com.example.demo.service;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Account;
import com.example.demo.entity.GeneralUser;
import com.example.demo.entity.JournalDetail;
import com.example.demo.entity.JournalEntry;
import com.example.demo.form.JournalEntryForm;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.GeneralUserRepository;
import com.example.demo.repository.JournalDetailRepository;
import com.example.demo.repository.JournalEntryRepository;

@Service
public class JournalEntryService {

    private final JournalEntryRepository journalEntryRepository;
    private final GeneralUserRepository generalUserRepository;
    private final JournalDetailRepository journalDetailRepository;
    private final AccountRepository accountRepository;
   

    @Autowired
    public JournalEntryService(JournalEntryRepository journalEntryRepository,
                               GeneralUserRepository generalUserRepository,
                               JournalDetailRepository journalDetailRepository,
                               AccountRepository accountRepository) {
        this.journalEntryRepository = journalEntryRepository;
        this.generalUserRepository = generalUserRepository;
        this.journalDetailRepository = journalDetailRepository;
        this.accountRepository = accountRepository;
    }
  
    @Transactional
    public JournalEntry createJournalEntry(Integer generalId, JournalEntryForm journalEntryForm) {
        JournalEntry journalEntry = new JournalEntry();

        // Retrieve general user from repository
        GeneralUser generalUser = generalUserRepository.findByGeneralId(generalId).orElse(null);
        if (generalUser == null) {
            // Handle case where general user is not found
            return null; // Or perform another appropriate action if user not found
        }

        // Set up JournalEntry details
        journalEntry.setGeneralUser(generalUser);
        journalEntry.setEntryDate(journalEntryForm.getDate());
        journalEntry.setDescription(journalEntryForm.getDescription());
        journalEntry.setTotalAmount(journalEntryForm.getTotalAmount());
        journalEntry.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        journalEntry.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        // Save JournalEntry
        JournalEntry savedJournalEntry = journalEntryRepository.save(journalEntry);

        // Create and save JournalDetail
        JournalDetail journalDetail = new JournalDetail();
        journalDetail.setJournalEntry(savedJournalEntry);

        Account debitAccount = accountRepository.findByAccountCode(journalEntryForm.getDebitAccount());
        Account creditAccount = accountRepository.findByAccountCode(journalEntryForm.getCreditAccount());

        journalDetail.setDebitAccount(debitAccount);
        journalDetail.setCreditAccount(creditAccount);
        journalDetail.setDebitAmount(journalEntryForm.getTotalAmount());
        journalDetail.setCreditAmount(journalEntryForm.getTotalAmount());
        journalDetail.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        journalDetail.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        journalDetailRepository.save(journalDetail);

        return savedJournalEntry;
    }

    

    
    public List<Object[]> getEntriesWithDetailsByGeneralId(Integer generalId) {
        return journalEntryRepository.findEntriesWithDetailsByGeneralId(generalId);
    }
    
    public void deleteJournalEntry(Integer entryId) {
        JournalEntry entry = journalEntryRepository.findById(entryId).orElse(null);
        System.out.println("Deleting journal entry with ID: " + entryId);
        if (entry != null) {
            entry.setDeletedAt(1);
            journalEntryRepository.save(entry);
        }
    }
}




