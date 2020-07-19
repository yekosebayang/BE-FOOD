package com.lstprjct.emcd.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lstprjct.emcd.entity.TransactionDetail;

public interface TransactionDetailRepo extends JpaRepository<TransactionDetail, Integer>{

	@Query(value= "SELECT * from transaction_detail WHERE transaction_id= ?1", nativeQuery = true)
	public Iterable<TransactionDetail> getDetailById(int transId);
	
}
