package com.holz.web.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ManageController {
	
	@RequestMapping(value = { "/manage**" }, method = RequestMethod.GET)
	public ModelAndView Manage(Principal principal) {
		ModelAndView model = new ModelAndView("manager.manage");
		return model;
	}
	
}
