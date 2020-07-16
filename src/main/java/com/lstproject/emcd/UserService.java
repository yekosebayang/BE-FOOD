package com.lstproject.emcd;

import com.lstprjct.emcd.entity.User;

public interface UserService {

	public User addUser(User user);

	public Iterable<User> getAllUser();

	public User editUserById(User user, int userId);

}
