package com.lstprjct.emcd.service;

import com.lstprjct.emcd.entity.Category;

public interface CategoryService {

	Category addCategory(Category category);

	Iterable<Category> readCategory();

	Category editCategoryById(Category category, int categoryId);

	void deleteCategoryById(int categoryId);
	
}


