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

    @Column(name = "login_id", nullable = false, unique = true, length = 255)
    private Short generalLoginId;  // 変更: String型に変更

    @Column(name = "password", nullable = false, length = 255)
    private String generalPassword;  // 変更: nullable=falseとlengthを追加

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private AdminUser adminUser;
}
