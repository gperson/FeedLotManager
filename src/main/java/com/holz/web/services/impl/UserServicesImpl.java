package com.holz.web.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.holz.web.daos.UserDao;
import com.holz.web.models.User;
import com.holz.web.services.UserServices;

@Transactional
@Service
public class UserServicesImpl implements UserServices {

	@Autowired
	private UserDao userDao;

	@Override
	public List<User> getUsersForFarm(int farmId) {		
		return this.userDao.getUsersForFarm(farmId);
	}

	@Override
	public void saveOrUpdateUser(User user, int farmId) {
		this.userDao.saveOrUpdate(user, farmId);		
	}

	@Override
	public void resetPassword(String username) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		//TODO Send email to reset password
		this.userDao.updatePassword(username, passwordEncoder.encode("ChangeMe"));
	}

}
