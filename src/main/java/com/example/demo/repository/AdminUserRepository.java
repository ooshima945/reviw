package com.example.demo.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.AdminUser;

public interface AdminUserRepository extends JpaRepository<AdminUser,Integer> {

    Optional<AdminUser> findByAdminEmail(String adminEmail);
    List<AdminUser> findAll();
}
