package com.holz.web.daos;

import java.util.List;

import com.holz.web.models.User;

public interface UserDao {

	List<User> getUsersForFarm(int farmId);
	
	void saveOrUpdate(User user, int farmId);
	
	void enableDisableUser(User user, int farmId);

	void updatePassword(String username, String password, boolean isReset);

	User getUser(String usernameOrEmail, boolean useEmail);

	List<String> getAdminUserEmailsForFarm(int farmId);
	
}
