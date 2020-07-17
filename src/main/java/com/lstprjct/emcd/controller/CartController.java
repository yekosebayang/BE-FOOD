package com.lstprjct.emcd.controller;

import javax.transaction.Transactional;

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

import com.lstprjct.emcd.dao.CartRepo;
import com.lstprjct.emcd.dao.ProductRepo;
import com.lstprjct.emcd.dao.UserRepo;
import com.lstprjct.emcd.entity.Cart;
import com.lstprjct.emcd.entity.Product;
import com.lstprjct.emcd.entity.User;

@RestController
@RequestMapping("/carts")
@CrossOrigin
public class CartController {
	@Autowired private CartRepo cartRepo;
	
	@Autowired private ProductRepo productRepo;
	
	@Autowired private UserRepo userRepo;
	
	@GetMapping
    public Iterable<Cart> getAllCart() {
        return cartRepo.findAll();
    }
	
	@GetMapping("/user/{userId}")
    public Iterable<Cart> getCartByUser(@PathVariable int userId) {
        return cartRepo.findByUserId(userId);
    }
	
	@GetMapping("/product/{productId}")
    public Iterable<Cart> getCartByProduct(@PathVariable int productId){
        return cartRepo.findByProductId(productId);
    }
	
	@GetMapping("/get/{userId}/{productId}")
	public Iterable<Cart> getCartByProductandUserId(@PathVariable int userId, @PathVariable int productId) { 
		return cartRepo.findCartId(userId, productId);
	}
	
	@PostMapping("/addnew/{userId}/{productId}")
    @Transactional
    public Cart addToCart(@RequestBody Cart cartData, @PathVariable int userId, @PathVariable int productId){
        Product findProduct = productRepo.findById(productId).get();
        User findUser = userRepo.findById(userId).get();
        cartData.setProduct(findProduct);
        cartData.setUser(findUser);
        return cartRepo.save(cartData);
    }
	
	 @PutMapping("/{cartId}/qty/{cartTotal}")
	 public Cart updateCartQty (@PathVariable int cartId, @PathVariable int cartTotal){
		 Cart findCartData = cartRepo.findById(cartId).get();
	        if (cartTotal == +1) {
	        	findCartData.setQuantity(findCartData.getQuantity() + 1);
	        	return cartRepo.save(findCartData);
	        }
	        findCartData.setQuantity(cartTotal);
	        return cartRepo.save(findCartData);
	    }
	
	 @DeleteMapping("/{cartId}")
	 public void deleteOneCartFromUser (@PathVariable int cartId) {
		 cartRepo.deleteById(cartId);		 
	 	}
	 
	 @DeleteMapping("/all/{userId}")
	 public void deleteAllCartFromUser (@PathVariable int userId) {
		 Iterable<Cart> findCart = cartRepo.findByUserId(userId);
		 
		 cartRepo.deleteAll(findCart);
	 }
	 
}