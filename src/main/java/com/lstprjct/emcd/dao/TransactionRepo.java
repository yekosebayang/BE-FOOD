package com.lstprjct.emcd.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lstprjct.emcd.entity.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction, Integer> {

	@Query(value = "SELECT * FROM transaction WHERE user_id= ?1", nativeQuery = true)
    public Iterable<Transaction> findByUserId(int userId);
}
