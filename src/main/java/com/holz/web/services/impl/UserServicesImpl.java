package com.holz.web.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
	
	@Autowired 
	private JavaMailSender mailSender;

	@Override
	public List<User> getUsersForFarm(int farmId) {		
		return this.userDao.getUsersForFarm(farmId);
	}

	@Override
	public void saveOrUpdateUser(User user, int farmId) {
		if(user.getId() == 0){
			changePassword(user);
		}
		this.userDao.saveOrUpdate(user, farmId);		
	}

	@Override
	public void resetPassword(String usernameOrEmail, boolean useEmail) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();		
		SimpleMailMessage message = new SimpleMailMessage();
		User user = new User();
		if(useEmail){
			user.setEmail(usernameOrEmail);
		} else {
			user.setUsername(usernameOrEmail);
		}
		String password = changePassword(user);
		user = this.userDao.getUser(usernameOrEmail,useEmail);
		message.setTo(user.getEmail());
		message.setSubject("Password for Feed Lot Manager");
		message.setText("Login information for user:\nUsername: "+user.getUsername()+"\nPassword: "+password);
		this.mailSender.send(message);
		this.userDao.updatePassword(user.getUsername(), passwordEncoder.encode(password),true);
	}
	
	@Override
	public boolean changePassword(String username, String password1, String password2) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();		
		User user = new User();
		user.setUsername(username);
		if(password1.equals(password2)){
			this.userDao.updatePassword(username, passwordEncoder.encode(password1),false);
		} else {
			return false;
		}
		return true;
	}

	@Override
	public void enableDisableUser(User user, int farmId) {
		this.userDao.enableDisableUser(user, farmId);
	}

	@Override
	public User getUser(String username) {
		return this.userDao.getUser(username,false);
	}

	private String changePassword(User user){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String uuid = UUID.randomUUID().toString();
		user.setPassword(passwordEncoder.encode(uuid));
		return uuid;
	}
}
