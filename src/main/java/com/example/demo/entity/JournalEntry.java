package com.example.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;

@Entity
@Table(name = "journal_entries")
@Data
public class JournalEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "entry_id")
    private Integer entryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "general_id", nullable = false)
    private GeneralUser generalUser;

    @Column(name = "entry_date", nullable = false)
    private LocalDate entryDate;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "total_amount", precision = 19, scale = 4)
    private BigDecimal totalAmount;

    @Column(name = "created_at", nullable = false, updatable = false)
    private java.sql.Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    @Version
    private java.sql.Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Integer deletedAt;

    
    @OneToMany(mappedBy = "journalEntry", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<JournalDetail> journalDetails;

    
   

}
