package com.lstprjct.emcd.controller;

import java.security.Key;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lstprjct.emcd.dao.TransactionRepo;
import com.lstprjct.emcd.entity.Transaction;
import com.lstprjct.emcd.entity.User;
import com.lstprjct.emcd.util.EmailUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@RestController
@RequestMapping("/invoice")
@CrossOrigin
public class InvoiceController {
	@Autowired private TransactionRepo transactionRepo;
	
	@Autowired
	private EmailUtil emailutil;
	
	@PostMapping("/complete/{transId}")
	public String completeTrans(@PathVariable int transId) {
		Transaction findTrans = transactionRepo.findById(transId).get();
		User findUser = findTrans.getUser();
		
		emailutil.sendEmail(findUser.getUseremail(), 
				"Invoice",
				"<h1>Hallo, "+ findUser.getUsername() +"</h1> <br> "
				+ "Id Transaksi: "+ transId +"<br>"
				+ "Total: "+ findTrans.getTotalprice() +"<br>"
				+ "Terimakasih."	
				);

		return "Success";
		
	}
	
	
}
