package com.lstprjct.emcd.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lstprjct.emcd.entity.Category;
import com.lstprjct.emcd.entity.Product;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

	Optional<Product> findByCategoryname(String categoryname);

}
