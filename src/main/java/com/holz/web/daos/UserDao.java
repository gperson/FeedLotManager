package com.holz.web.daos;

import java.util.List;

import com.holz.web.models.User;

public interface UserDao {

	List<User> getUsersForFarm(int farmId);
	
	void saveOrUpdate(User user, int farmId);
	
	void enableDisableUser(User user, int farmId);

	User getUser(String username);

	void updatePassword(String username, String password, boolean isReset);
	
}
