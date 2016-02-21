package com.holz.web.services;

import java.util.List;

import com.holz.web.models.User;

public interface UserServices {

	List<User> getUsersForFarm(int farmId);
	
	void saveOrUpdateUser(User user,int farmId);

	void enableDisableUser(User user, int farmId);

	User getUser(String username);

	boolean changePassword(String username, String password1, String password2);

	void resetPassword(String usernameOrEmail, boolean useEmail);
}
