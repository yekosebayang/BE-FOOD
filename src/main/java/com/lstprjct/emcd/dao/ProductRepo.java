package com.lstprjct.emcd.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lstprjct.emcd.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Integer> {

	Optional<Product> findByProductname(String productname);

}
