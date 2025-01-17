package com.lstprjct.emcd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lstprjct.emcd.entity.Product;
import com.lstprjct.emcd.service.ProductService;


@RestController
@RequestMapping("/products")
@CrossOrigin
public class ProductController {
	
	
	@Autowired
	private ProductService productService;
	
	@PostMapping
	@RequestMapping("/new")
	public Product addProducts(@RequestBody Product product) {
		return productService.addProducts(product);
	}
	
	@GetMapping("/all")
	public Iterable<Product> getAllProducts(){
		return productService.getAllProducts();
	}
	
	@GetMapping("/{productId}")
	public Product getProductById(@PathVariable int productId){
		return productService.readProductById(productId);
	}
	
	@PutMapping("/edit/{productId}")
	public Product editImageProductById(@RequestBody Product product, @PathVariable int productId) {
		return productService.editImageProductById(product, productId);
	}
	
	@PostMapping("{productId}/category/{cateId}")
	public Product addCategoryToProduct(@PathVariable int productId, @PathVariable int cateId) {
		return productService.addCategoryToProduct(productId, cateId);
	}
	
	@PutMapping("pict/{productId}")
	public String uploadPict(@RequestParam("file") MultipartFile file, @PathVariable int productId) {
		return productService.uploadPict(file, productId);
	}
	
	@GetMapping("/download/{fileName:.+}")
	public ResponseEntity<Object> downloadPict(@PathVariable String fileName) {
		return productService.downloadPict(fileName);
	}
		
	@DeleteMapping("{productId}/cate/{cateId}")
	public Product deleteCategoryInProduct(@PathVariable int productId, @PathVariable int cateId) {
		return productService.deleteCategoryInProduct(productId, cateId);
	}
	
	@DeleteMapping("/{productId}")
	public void deleteProductbyId(@PathVariable int productId) {
		productService.deleteProductbyId(productId);
	}
	
	@PutMapping("/sold/{productId}/{qty}")
	public Product editSoldProductById(@PathVariable int productId, @PathVariable int qty) {
		return productService.editSoldProductById(productId, qty);
	}
	
	@PostMapping("{productId}/paket/{paketId}")
	public Product addPaketToProduct(@PathVariable int productId, @PathVariable int paketId) {
		return productService.addPaketToProduct(productId, paketId);
	}
	
	@DeleteMapping("{productId}/paket/{paketId}")
	public Product deletePaketInProduct(@PathVariable int productId, @PathVariable int paketId) {
		return productService.deletepaketInProduct(productId, paketId);
	}
	
	@PutMapping("/stock/{productId}/{qty}")
	public Product editStockProductById(@PathVariable int productId, @PathVariable int qty) {
		return productService.editStockProductById(productId, qty);
	}
		
}
