package com.lstprjct.emcd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lstprjct.emcd.entity.User;
import com.lstprjct.emcd.service.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	@RequestMapping("/new")
	public User addUser(@RequestBody User user) {
		return userService.addUser(user);
	}
	
	@GetMapping("/all")
	public Iterable<User> getAllUser() {
		return userService.getAllUser();
	}
	
	
	@GetMapping("/{userId}")
	public User KeeploginUser(@PathVariable int userId) {
		return userService.KeeploginUser(userId);
	}
	
	@GetMapping
	public User loginUser(@RequestParam String username, @RequestParam String password) {
		return userService.loginUser(username, password);
	}
	
	@GetMapping("/login")
	public User loginUser2(@RequestBody User user) {
		return userService.loginUser2(user);
	}
	
	@GetMapping("/edit-verif/{userId}")
	public User editVerifiedById(@PathVariable int userId) {
		return userService.editVerifiedById(userId);
	}
	
	@PostMapping("/req-verif")
	public User reqVerif(@RequestBody User user) {
		return userService.reqVerif(user);
	}
	
	@PostMapping("/forget")
	public String reqForget(@RequestBody User user) {
		return userService.reqForget(user);
	}
	
	@PutMapping("/reset")
	public String editPasswordByToken(@RequestBody User user) {
		return userService.editPasswordByToken(user);
	}
	
	@PutMapping("/edit/password/{userId}")
	public User editUserPasswordById(@RequestBody User user, @PathVariable int userId) {
		return userService.editUserPasswordById(user, userId);
	}
	
	@PutMapping("/edit/{userId}")
	public User editUserById(@RequestBody User user, @PathVariable int userId) {
		return userService.editUserById(user, userId);
	}
}
