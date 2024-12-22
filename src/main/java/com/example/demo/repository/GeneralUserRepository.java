package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.GeneralUser;

public interface GeneralUserRepository extends JpaRepository<GeneralUser, Integer> {

    // generalLoginId に基づいてユーザーを検索
    Optional<GeneralUser> findByGeneralLoginId(Short loginId);

    // generalId に基づいてユーザーを検索
    Optional<GeneralUser> findByGeneralId(Integer generalId);
   
    Page<GeneralUser> findByAdminUser_AdminIdAndDeletedAtIsNull(Integer adminId, Pageable pageable);
    
    List<GeneralUser> findAllByAdminUserAdminId(Integer adminId); // adminIdが一致するすべての一般ユーザーを取得
    
   
    
    @Query("SELECT g.generalId FROM GeneralUser g WHERE g.adminUser.adminId = :adminId")
    List<Integer> findIdsByAdminId(Integer adminId);
    
    @Query("SELECT g.adminUser.adminId FROM GeneralUser g WHERE g.generalId = :generalId")
    Integer findAdminIdByGeneralId(Integer generalId);

	
    
  
   }
   




