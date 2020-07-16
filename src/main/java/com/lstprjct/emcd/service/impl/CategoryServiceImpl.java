package com.lstprjct.emcd.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lstprjct.emcd.dao.CategoryRepo;
import com.lstprjct.emcd.dao.ProductRepo;
import com.lstprjct.emcd.entity.Category;
import com.lstprjct.emcd.service.CategoryService;

@Service
// jangan lupa di dedclare dia implemen dari mana
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ProductRepo productRepo;

	@Override
	public Category addCategory(Category category) {
		category.setId(0);
		return categoryRepo.save(category);
	}
	
	@Override
	public Iterable<Category> readCategory() {
		return categoryRepo.findAll();
	}

	@Override
	public void deleteCategoryById(int categoryId) {
		Category findCategory = categoryRepo.findById(categoryId).get();
		
		findCategory.getProduct().forEach(movie -> {
			movie.getCategory().remove(findCategory);
			productRepo.save(movie);
		});
		
		findCategory.setProduct(null);
		categoryRepo.deleteById(categoryId);
	}

	@Override
	public Category editCategoryById(Category category, int categoryId) {
		category.setId(categoryId);
		Optional<Category> findCategory = categoryRepo.findById(categoryId);
		if (findCategory.toString() == "Optional.empty")
			throw new RuntimeException("Id not exist, ID: " + categoryId);
		return categoryRepo.save(category);
	}
}
