package com.holz.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

	@RequestMapping(value = { "/", "/home**" }, method = RequestMethod.GET)
	public ModelAndView Home() {
		ModelAndView model = new ModelAndView("manager.home");
		model.addObject("title", "Welcome");
		model.addObject("message", "This is default page!");
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
		model.addObject("title", "Welcome");
		model.addObject("message", "This is default page!");
		return model;
	}

}