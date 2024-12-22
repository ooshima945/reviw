package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

  
	Optional<Account> findOptionalByAccountCode(String accountCode); // Optionalを返すメソッド

	Account findByAccountCode(String accountCode);

	
}

