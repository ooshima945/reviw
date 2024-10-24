package com.example.demo.service;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.GeneralUser;
import com.example.demo.entity.JournalEntry;
import com.example.demo.form.JournalEntryForm;
import com.example.demo.repository.GeneralUserRepository;
import com.example.demo.repository.JournalEntryRepository;

@Service
public class JournalEntryService {

    private final JournalEntryRepository journalEntryRepository;
    private final GeneralUserRepository generalUserRepository;

    @Autowired
    public JournalEntryService(JournalEntryRepository journalEntryRepository, GeneralUserRepository generalUserRepository) {
        this.journalEntryRepository = journalEntryRepository;
        this.generalUserRepository = generalUserRepository;
    }

    @Transactional
    public JournalEntry createJournalEntry(JournalEntryForm journalEntryForm) {
        JournalEntry journalEntry = new JournalEntry();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginId = (String) authentication.getPrincipal();

        GeneralUser generalUser = generalUserRepository.findByGeneralId(Integer.parseInt(loginId))
                .orElseThrow(() -> new IllegalArgumentException("General user not found: " + loginId));

        journalEntry.setGeneralUser(generalUser);
        journalEntry.setEntryDate(journalEntryForm.getDate());
        journalEntry.setDescription(journalEntryForm.getDescription());
        journalEntry.setTotalAmount(journalEntryForm.getTotalAmount());
        journalEntry.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        journalEntry.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

        return journalEntryRepository.save(journalEntry);
    }
}
