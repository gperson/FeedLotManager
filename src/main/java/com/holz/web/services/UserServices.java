package com.holz.web.services;

import java.util.List;

import com.holz.web.models.User;

public interface UserServices {

	List<User> getUsersForFarm(int farmId);
	
	void saveOrUpdateUser(User user,int farmId);
	
	void resetPassword(String username);

	void enableDisableUser(User user, int farmId);
}