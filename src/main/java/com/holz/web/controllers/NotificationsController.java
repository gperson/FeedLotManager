package com.holz.web.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.holz.web.models.User;
import com.holz.web.services.NotificationServices;

@Controller
public class NotificationsController {
	
	@Autowired
	private NotificationServices notificationServices;
	
	@RequestMapping(value = "/notifications/optiflex", method = RequestMethod.GET, produces="application/json", consumes="application/json")
	public void Optiflex(@RequestBody User user) {
		if(user.getUsername().equals("***") && user.getPassword().equals("***")) //TODO Make better
			this.notificationServices.SendOptiflexReminders();
	}
	//curl -H 'Accept: application/json' -H 'Content-Type: application/json' -X GET -d '{ "username": "***", "password" :"***"}' -k http://52.36.251.151:8080/notifications/optiflex
}
