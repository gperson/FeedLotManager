package com.holz.web.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.holz.web.services.FarmServices;

@Controller
public class HomeController {

	@Autowired 
	FarmServices farmServices;
	
	@RequestMapping(value = { "/", "/home**" }, method = RequestMethod.GET)
	public ModelAndView Home(Principal principal) {
		ModelAndView model = new ModelAndView("manager.home");	
		model.addObject("title", this.farmServices.getFarmByUserName(principal.getName()).getFarmName());
		return model;
	}

}