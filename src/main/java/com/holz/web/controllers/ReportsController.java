package com.holz.web.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReportsController {
	
	@RequestMapping(value = { "/reports**" }, method = RequestMethod.GET)
	public ModelAndView Manage(Principal principal) {
		ModelAndView model = new ModelAndView("manager.reports");
		return model;
	}
	
}
