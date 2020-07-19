package com.lstprjct.emcd.service.impl;

import java.security.Key;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lstprjct.emcd.entity.User;
import com.lstprjct.emcd.service.UserService;
import com.lstprjct.emcd.util.EmailUtil;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import com.lstprjct.emcd.dao.UserRepo;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private EmailUtil emailutil;
	
	private PasswordEncoder pwEncoder = new BCryptPasswordEncoder();

	@Override
	@Transactional
	public User addUser(User user) {
		user.setId(0);
		Optional<User> findUser = userRepo.findByUsername(user.getUsername());
		Optional<User> findEmail = userRepo.findByUseremail(user.getUseremail()); 
		
		if (findUser.toString() != "Optional.empty") {
			throw new RuntimeException("Username sudah terdaftar!");
		}
		if (findEmail.toString() != "Optional.empty") {
			throw new RuntimeException("Email sudah terdaftar!");
		}
		String encodedPassword = pwEncoder.encode(user.getPassword());

		user.setPassword(encodedPassword);
		
		return userRepo.save(user);
	}

	@Override
	public Iterable<User> getAllUser() {
		return userRepo.findAll();
	}
	
	@Override
	public User loginUser(String username, String password) {
		User findUser = userRepo.findByUsername(username).get();		

		if (findUser.toString() == "Optional.empty") {
			throw new RuntimeException("Username belum terdaftar!");
		}
			
		if (pwEncoder.matches(password, findUser.getPassword())) {
			return findUser;
		}
		throw new RuntimeException("Password salah!");
	}
	
	
	@Override
	public User loginUser2(User user) {
		User findUser = userRepo.findByUsername(user.getUsername()).get();		

		if (findUser.toString() == "Optional.empty") {
			throw new RuntimeException("Username belum terdaftar!");
		}
			
		if (pwEncoder.matches(user.getPassword(), findUser.getPassword())) {
			return findUser;
		}
		throw new RuntimeException("Password salah!");
	}
	
	@Override
	public User KeeploginUser(int userId) {
		User findUser = userRepo.findById(userId).get();
		return findUser;
	}

	@Override
	public User editVerifiedById(int userId) {
		User findUser = userRepo.findById(userId).get();

		findUser.setVerified("sudah");
		return userRepo.save(findUser);
	}

	@Override
	public User reqVerif(User user) {
		emailutil.sendEmail(user.getUseremail(), 
				"Verifikasi Akun",
				"<h1>Hallo, "+ user.getUsername() +"</h1> <br> "
				+ "Klik <a href=\"http://localhost:8081/users/edit-verif/"+ user.getId()+"\">link</a> ini untuk verifikasi email anda "	
				);
		return null;		
	}

	@Override
	public String reqForget(User user) {
		User findEmail = userRepo.findByUseremail(user.getUseremail()).get();
		
		Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		String jws = Jwts.builder().setSubject(findEmail.getUseremail()).signWith(key).compact();
		
		emailutil.sendEmail(findEmail.getUseremail(), 
				"Atur ulang kata sandi",
				"<h1>Hallo, "+ findEmail.getUsername() +"</h1> <br> "
				+ "Klik <a href=\"http://localhost:3000/reset-password/"+jws+"\">link</a> ini untuk verifikasi email anda <br>"
				+ "jika anda tidak menginginkan pengaturan ulang kata sandi, abaikan pesan ini!"	
				);

		findEmail.setToken(jws);
		userRepo.save(findEmail);
//		
		return findEmail.getUsername();
//		return jws;
	}

	@Override
	public String editPasswordByToken(User user) {
		User findUser = userRepo.findByToken(user.getToken()).get();
		String encodedPassword = pwEncoder.encode(user.getPassword());
		
		findUser.setPassword(encodedPassword);
		findUser.setToken(null);
		userRepo.save(findUser);
		return "success";
	}

	@Override
	public User editUserPasswordById(User user, int userId) {
		user.setId(userId);								
		String encodedPassword = pwEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		return userRepo.save(user);
	}

	@Override
	public User editUserById(User user, int userId) {
		User findUser = userRepo.findById(userId).get();
		user.setPassword(findUser.getPassword());
		return userRepo.save(user);
	}
}
