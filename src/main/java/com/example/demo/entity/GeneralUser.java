package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "general_users")
@Data
public class GeneralUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "general_id")
    private Integer generalId;

    @Column(name = "login_id")
    private short generalLoginId;

    @Column(name = "password")
    private String generalPassword;



    @ManyToOne
    @JoinColumn(name = "admin_id")
    private AdminUser adminUser;
}
