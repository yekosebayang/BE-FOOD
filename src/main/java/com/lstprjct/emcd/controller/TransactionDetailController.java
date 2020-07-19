package com.lstprjct.emcd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lstprjct.emcd.dao.ProductRepo;
import com.lstprjct.emcd.dao.TransactionDetailRepo;
import com.lstprjct.emcd.dao.TransactionRepo;
import com.lstprjct.emcd.entity.Product;
import com.lstprjct.emcd.entity.Transaction;
import com.lstprjct.emcd.entity.TransactionDetail;

@RestController
@RequestMapping("/details")
@CrossOrigin
public class TransactionDetailController {
	@Autowired
	TransactionDetailRepo detailRepo;
	
	@Autowired
	TransactionRepo transactionRepo;
	
	@Autowired
	ProductRepo productRepo;
	
	@PostMapping("/{transId}/{prodId}")
	public TransactionDetail postTransDetail(@PathVariable int transId, @PathVariable int prodId, @RequestBody TransactionDetail body) {
		Transaction findTranscation = transactionRepo.findById(transId).get();
		Product findProduct = productRepo.findById(prodId).get();
		body.setProduct(findProduct);
		body.setTransaction(findTranscation);
		return detailRepo.save(body);
	}
	
	@GetMapping("/acc/{transId}")
	public Iterable<TransactionDetail> getAccTransaction(@PathVariable int transId) {
		return detailRepo.getDetailById(transId);
		
	}
}
