package com.holz.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.holz.web.models.Locale;
import com.holz.web.models.enums.FarmLoadOption;
import com.holz.web.services.FarmServices;

@Controller
public class MainController {

	@Autowired 
	FarmServices farmServices;
	
	@RequestMapping(value = { "/", "/home**" }, method = RequestMethod.GET)
	public ModelAndView Home() {
		ModelAndView model = new ModelAndView("manager.home");		
		model.addObject("title", this.farmServices.getFarmByUserName(getUserName(),FarmLoadOption.FARM_NAME_AND_ID).getFarmName());
		return model;
	}

	@RequestMapping(value = { "/mixFeed**" }, method = RequestMethod.GET)
	public ModelAndView MixFeed() {
		ModelAndView model = new ModelAndView("manager.mixFeed");
		model.addObject("title", "Welcome");
		model.addObject("message", "This is default page!");
		return model;
	}

	@RequestMapping(value = { "/deliver**" }, method = RequestMethod.GET)
	public ModelAndView Deliver() {
		ModelAndView model = new ModelAndView("manager.deliver");
		model.addObject("title", "Welcome");
		model.addObject("message", "This is default page!");
		return model;
	}

	@RequestMapping(value = { "/pickLocation**" }, method = RequestMethod.GET)
	public ModelAndView PickLocation() {
		ModelAndView model = new ModelAndView("manager.pickLocation");
		model.addObject("farm",this.farmServices.getFarmByUserName(getUserName(),FarmLoadOption.LOCALES_WITH_HERDS));
		return model;
	}
		
	@ResponseBody
	@RequestMapping(value = "/pickedLocation", method = RequestMethod.POST)
	public boolean getSearchResultViaAjax(@RequestBody Locale locale) {
		return true;

	}

	private String getUserName(){
		return ((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
	}
}