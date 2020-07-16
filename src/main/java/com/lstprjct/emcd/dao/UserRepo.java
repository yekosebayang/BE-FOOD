package com.lstprjct.emcd.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lstprjct.emcd.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {

	public Optional<User> findByUsername(String username);

	public Optional<User> findByUseremail(String useremail);

	public Optional<User> findByToken(String token);
}
