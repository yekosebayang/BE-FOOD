package com.lstprjct.emcd.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.lstprjct.emcd.entity.Category;
import com.lstprjct.emcd.entity.Product;

public interface ProductService {

	Product addProducts(Product product);

	Iterable<Product> getAllProducts();

	Product readProductById(int productId);

	Product editImageProductById(Product product, int productId);

	Product addCategoryToProduct(int productId, int cateId);

	String uploadPict(MultipartFile file, int productId);

	ResponseEntity<Object> downloadPict(String fileName);

	Product deleteCategoryInProduct(int productId, int cateId);

	void deleteProductbyId(int productId);

	Product editSoldProductById(int productId, int qty);

}
