package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.GeneralUser;

public interface GeneralUserRepository extends JpaRepository<GeneralUser, Integer> {

    // generalLoginId に基づいてユーザーを検索
    Optional<GeneralUser> findByGeneralLoginId(short generalLoginId);

    // generalId に基づいてユーザーを検索
    Optional<GeneralUser> findByGeneralId(Integer generalId);
    List<GeneralUser> findByAdminUser_AdminId(Integer adminId);
}



