package com.lstprjct.emcd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lstprjct.emcd.entity.Category;
import com.lstprjct.emcd.service.CategoryService;

@RestController // dia kontroller
@RequestMapping("/category")
@CrossOrigin
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping //dia post command
	@RequestMapping("/new")
	public Category addCategory(@RequestBody Category category) {
		return categoryService.addCategory(category); 	
	}
	
	@GetMapping
	public Iterable<Category> readCategory(){
		return categoryService.readCategory();
	}
		
	@PutMapping("/edit/{categoryId}")
	public Category editCategoryById(@RequestBody Category category, @PathVariable int categoryId) {
		return categoryService.editCategoryById(category, categoryId);
	}
		
	@DeleteMapping("{categoryId}")
		public void deleteCategoryById(@PathVariable int categoryId) {
	categoryService.deleteCategoryById(categoryId);
	}

}
