package com.holz.web.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
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
public class FeedingController {
	
	@Autowired 
	FarmServices farmServices;
	
	@RequestMapping(value = { "/feedMix**" }, method = RequestMethod.GET)
	public ModelAndView MixFeed(Principal principal) {
		ModelAndView model = new ModelAndView("manager.feedMix");
		//model.addObject("farm",this.farmServices.getFarmByUserName(principal.getName(),FarmLoadOption.LOCALES_WITH_HERDS));
		return model;
	}

	@RequestMapping(value = { "/deliver**" }, method = RequestMethod.GET)
	public ModelAndView Deliver(Principal principal) {
		ModelAndView model = new ModelAndView("manager.deliver");
		model.addObject("title", "Welcome");
		model.addObject("message", "This is default page!");
		return model;
	}

	@RequestMapping(value = { "/pickLocation**" }, method = RequestMethod.GET)
	public ModelAndView PickLocation(Principal principal) {
		ModelAndView model = new ModelAndView("manager.pickLocation");
		model.addObject("farm",this.farmServices.getFarmByUserName(principal.getName(),FarmLoadOption.LOCALES_WITH_HERDS));
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/pickedLocation", method = RequestMethod.POST)
	public boolean getSearchResultViaAjax(@RequestBody Locale locale,Principal principal) {
		return true;

	}

	@RequestMapping(value = { "/editFeeding**" }, method = RequestMethod.GET)
	public ModelAndView Manage(Principal principal) {
		ModelAndView model = new ModelAndView("manager.editFeeding");
		return model;
	}
}
