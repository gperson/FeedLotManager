package com.holz.web.controllers.tabs;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UsersTabController {
	
	@RequestMapping(value = { "/admin/usersTab" }, method = RequestMethod.GET)
	public ModelAndView UsersTab(Principal principal) {
		ModelAndView model = new ModelAndView("templates/manage/users_tab");
		return model;
	}
	
	
}
