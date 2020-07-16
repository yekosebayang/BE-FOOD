package com.lstprjct.emcd.service;

import com.lstprjct.emcd.entity.User;

public interface UserService {

	public User addUser(User user);

	public Iterable<User> getAllUser();
	
	public User loginUser(String username, String password);
	
	public User loginUser2(User user);

	public User editVerifiedById(int userId);

	public User reqVerif(User user);

	public User KeeploginUser(int userId);

	public String reqForget(User user);

	public String editPasswordByToken(User user);

	public User editUserPasswordById(User user, int userId);

	public User editUserById(User user, int userId);
}
