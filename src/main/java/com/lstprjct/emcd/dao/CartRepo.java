package com.lstprjct.emcd.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lstprjct.emcd.entity.Cart;

public interface CartRepo extends JpaRepository<Cart, Integer> {

	@Query(value = "SELECT * FROM cart WHERE user_id= ?1", nativeQuery = true)
    public Iterable<Cart> findByUserId(int userId);

	@Query(value = "SELECT * FROM cart WHERE product_id= ?1", nativeQuery = true)
    public Iterable<Cart> findByProductId(int productId);
	
	@Query(value = "SELECT * FROM cart WHERE user_id= ?1 and product_id= ?2", nativeQuery = true)
    public Iterable<Cart> findCartId(int userId, int productId );
}
