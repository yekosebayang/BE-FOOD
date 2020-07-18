package com.lstprjct.emcd.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lstprjct.emcd.dao.TransactionRepo;
import com.lstprjct.emcd.dao.UserRepo;
import com.lstprjct.emcd.entity.Transaction;
import com.lstprjct.emcd.entity.User;

@RestController
@RequestMapping("/transactions")
@CrossOrigin
public class TransactionController {
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private TransactionRepo transactionRepo;
	private String uploadPath = System.getProperty("user.dir") + 
			"\\src\\main\\resources\\static\\bill\\";
	
	@PostMapping("/new/{userId}")
	public Transaction addNewTransaction(@RequestBody Transaction transaction, @PathVariable int userId) {
		Date date = new Date();
		User findUser = userRepo.findById(userId).get();
		transaction.setUser(findUser);
		transaction.setBuydate(date);
		transaction.setPaydate(null);
		return transactionRepo.save(transaction);
	}
	
	@GetMapping("/user/{userId}")
	@Transactional
	private Iterable<Transaction> getTransactionByUserId(@PathVariable int userId) {
		return transactionRepo.findByUserId(userId);
	}
	
	@PutMapping("/payment/{transactionId}")
	@Transactional
	private Transaction uploadPayment(@RequestParam("file") MultipartFile file, @PathVariable int transactionId) {
		
		Transaction findTransaction = transactionRepo.findById(transactionId).get();
		Date date = new Date();
		if(!file.equals(null)) {
			String fileExtension = file.getContentType().split("/")[1];
			String fileName = "PAYMPIC-" + transactionId + "-"  + "-" + date.getTime() + "." + fileExtension;
			String newFileName = StringUtils.cleanPath(fileName);
			Path path = Paths.get(StringUtils.cleanPath(uploadPath) + newFileName);
			try {
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/transaction/download/")
					.path(fileName).toUriString();
			findTransaction.setTransactionbill(fileDownloadUri);
			findTransaction.setStatus("menunggu");
			findTransaction.setTransactiontext("Butki bayar anda sudah diterima");
			transactionRepo.save(findTransaction);
		}
		return findTransaction;
	}
}
