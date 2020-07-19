package com.lstprjct.emcd.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import com.lstprjct.emcd.dao.CategoryRepo;
import com.lstprjct.emcd.dao.PaketRepo;
import com.lstprjct.emcd.dao.ProductRepo;
import com.lstprjct.emcd.entity.Category;
import com.lstprjct.emcd.entity.Paket;
import com.lstprjct.emcd.entity.Product;
import com.lstprjct.emcd.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepo productRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private PaketRepo paketRepo;
	private String uploadPath = System.getProperty("user.dir") + 
			"\\src\\main\\resources\\static\\images\\";

	@Override
	public Product addProducts(Product product) {
		product.setId(0);
		product.setProductimage(null);
		Optional<Product> findProduct = productRepo.findByProductname(product.getProductname());
		if (findProduct.toString() != "Optional.empty") {
			throw new RuntimeException("Nama produk sudah terdaftar!");
		}
		return productRepo.save(product);
	}

	@Override
	public Iterable<Product> getAllProducts() {
		return productRepo.findAll();
	}

	@Override
	public Product readProductById(int productId) {
		Product findProduct = productRepo.findById(productId).get();
		if (findProduct == null) {
			throw new RuntimeException("Id: "+productId+"belum terdaftar!");
		}
		return findProduct;
	}

	@Override
	public Product editImageProductById(Product product, int productId) {
		product.setId(productId);
		Product findProduct = productRepo.findById(productId).get();
		product.setProductimage(findProduct.getProductimage());
		if (findProduct.toString() == "Optional.empty")
			throw new RuntimeException("Produk belum terdaftar, ID: " + productId);
		return productRepo.save(product);
	}

	@Override
	public Product addCategoryToProduct(int productId, int cateId) {
		Product findProduct = productRepo.findById(productId).get();
		Category findCategory = categoryRepo.findById(cateId).get();
		
		if (findProduct.getCategory().contains(findCategory)) {
			throw new RuntimeException("Kategori "+ findCategory.getCategoryname() +" sudah ditambahkan!");
		}
			
		findProduct.getCategory().add(findCategory);
		return productRepo.save(findProduct);
	}

	@Override
	public String uploadPict(MultipartFile file, int productId) {
		Product product = productRepo.findById(productId).get();
		Date date = new Date();

//		if (file.isEmpty()) {
		if(file.equals(null)) {
			productRepo.save(product);
			return "Gagal ganti foto";
		}
		
		String fileExtension = file.getContentType().split("/")[1];
		String fileName = "PRODPIC-" + productId + "-" + product.getProductname() + "-" + date.getTime() + "." + fileExtension;
		
//		System.out.println(fileExtension);
		
		String newFileName = StringUtils.cleanPath(fileName);
		Path path = Paths.get(StringUtils.cleanPath(uploadPath) + newFileName);
		
		try {
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/products/download/")
				.path(fileName).toUriString();
		
		product.setProductimage(fileDownloadUri);
		productRepo.save(product);
		
		return fileDownloadUri;
	}

	@Override
	public ResponseEntity<Object> downloadPict(String fileName) {
		Path path = Paths.get(uploadPath + fileName);
		Resource resource = null;
		
		try {
			resource = new UrlResource(path.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
//		System.out.println("DOWNLOAD");
		return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
				.header(HttpHeaders.CONTENT_DISPOSITION, 
				"attachment: filename=\"" + resource.getFilename() + "\"").body(resource);
	}

	@Override
	public Product deleteCategoryInProduct(int productId, int cateId) {
		Product findProduct = productRepo.findById(productId).get();
		Category findCategory = categoryRepo.findById(cateId).get();
		
		if (!findProduct.getCategory().contains(findCategory)) {
			throw new RuntimeException("Kategori "+ findCategory.getCategoryname() +" belum terdaftar!");
		}

		findProduct.getCategory().remove(findCategory);
		return productRepo.save(findProduct);
	}

	@Override
	public void deleteProductbyId(int productId) {
		Product findProduct = productRepo.findById(productId).get();
		findProduct.setCategory(null);
		productRepo.save(findProduct);
		productRepo.deleteById(productId);
	}

	@Override
	public Product editSoldProductById(int productId, int qty) {
		Product findProduct = productRepo.findById(productId).get();
		findProduct.setProductsold(findProduct.getProductsold() + qty);
		return productRepo.save(findProduct);
	}

	@Override
	public Product addPaketToProduct(int productId, int paketId) {
		Product findProduct = productRepo.findById(productId).get();
		Paket findPaket = paketRepo.findById(paketId).get();
			
		findProduct.getPaket().add(findPaket);
		return productRepo.save(findProduct);
	}

	@Override
	public Product deletepaketInProduct(int productId, int paketId) {
		Product findProduct = productRepo.findById(productId).get();
		Paket findPaket = paketRepo.findById(paketId).get();
		
		if (!findProduct.getPaket().contains(findPaket)) {
			throw new RuntimeException("Paket "+ findPaket.getPaketname() +" belum terdaftar!");
		}

		findProduct.getPaket().remove(findPaket);
		return productRepo.save(findProduct);
	}
}
