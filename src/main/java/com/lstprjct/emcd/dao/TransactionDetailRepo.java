package com.lstprjct.emcd.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lstprjct.emcd.entity.TransactionDetail;

public interface TransactionDetailRepo extends JpaRepository<TransactionDetail, Integer>{

}
