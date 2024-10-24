package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.JournalEntry;

public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {
}
