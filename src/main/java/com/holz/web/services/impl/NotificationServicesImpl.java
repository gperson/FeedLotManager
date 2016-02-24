package com.holz.web.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.holz.web.daos.HerdDao;
import com.holz.web.daos.UserDao;
import com.holz.web.models.Herd;
import com.holz.web.models.User;
import com.holz.web.services.NotificationServices;

@Transactional
@Service
public class NotificationServicesImpl implements NotificationServices {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private HerdDao herdDao;
	
	@Autowired 
	private JavaMailSender mailSender;
	
	@Override
	public void SendOptiflexReminders(){
		List<Herd> herds = this.herdDao.getHerdsInNeedOfOptiflex();
		
		if(herds != null && herds.size() > 0){
			int farmId = 0;
			SimpleMailMessage message = new SimpleMailMessage();
			String herdsStr = "";
			int i = 0;
			for(Herd h : herds){				
				herdsStr = herdsStr + ","+h.getHerdLabel();
				if(h.getFarmId() != farmId){
					//add
					String to = "";
					List<String> users = this.userDao.getAdminUserEmailsForFarm(h.getFarmId());
					for(String u : users){
						to = to + "," + u;
					}
					message.setTo(to.substring(1));
					message.setSubject("Optiflex Reminders");
				} else if(herds.size() > i+1 && herds.get(i+1).getFarmId() != farmId) {
					//Send old
					message.setText("The following are due for optiflex: "+herdsStr.substring(1));
					this.mailSender.send(message);
					
					//new make new
					herdsStr = "";
					message = new SimpleMailMessage();
				}
				farmId = h.getFarmId();
				i++;
			}
			message.setText("The following are due for optiflex: "+herdsStr.substring(1));
			this.mailSender.send(message);
		}
	}
}
