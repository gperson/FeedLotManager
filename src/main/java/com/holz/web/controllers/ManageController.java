package com.holz.web.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ManageController {
	
	@RequestMapping(value = { "/admin/manage**" }, method = RequestMethod.GET)
	public ModelAndView Manage(Principal principal) {
		ModelAndView model = new ModelAndView("manager.manage");
		return model;
	}
	
	@RequestMapping(value = { "/admin/locationsTab" }, method = RequestMethod.GET)
	public ModelAndView LocationsTab() {
		ModelAndView model = new ModelAndView("templates/manage/locations_tab");
		return model;
	}
	
	@RequestMapping(value = { "/admin/feedTab" }, method = RequestMethod.GET)
	public ModelAndView FeedTab() {
		ModelAndView model = new ModelAndView("templates/manage/feed_tab");
		return model;
	}
	
	@RequestMapping(value = { "/admin/livestockTab" }, method = RequestMethod.GET)
	public ModelAndView LivestockTab() {
		ModelAndView model = new ModelAndView("templates/manage/livestock_tab");
		return model;
	}
	
	@RequestMapping(value = { "/admin/packersTab" }, method = RequestMethod.GET)
	public ModelAndView PackersTab() {
		ModelAndView model = new ModelAndView("templates/manage/packers_tab");
		return model;
	}
	
	@RequestMapping(value = { "/admin/soldLivestockTab" }, method = RequestMethod.GET)
	public ModelAndView SoldLivestockTab() {
		ModelAndView model = new ModelAndView("templates/manage/soldLivestock_tab");
		return model;
	}
	
	@RequestMapping(value = { "/admin/suppliersTab" }, method = RequestMethod.GET)
	public ModelAndView SuppliersTab() {
		ModelAndView model = new ModelAndView("templates/manage/suppliers_tab");
		return model;
	}
	
	@RequestMapping(value = { "/admin/usersTab" }, method = RequestMethod.GET)
	public ModelAndView UsersTab() {
		ModelAndView model = new ModelAndView("templates/manage/users_tab");
		return model;
	}
}
