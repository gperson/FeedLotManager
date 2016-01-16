package com.holz.web.controllers.tabs;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.holz.web.services.FarmServices;
import com.holz.web.services.SupplierServices;

@Controller
public class SoldLivestockTabController {
	
	@Autowired 
	SupplierServices supplierServices;
	
	@Autowired 
	FarmServices farmServices;
	
	@RequestMapping(value = { "/admin/soldLivestockTab" }, method = RequestMethod.GET)
	public ModelAndView SoldLivestockTab(Principal principal) {
		ModelAndView model = new ModelAndView("templates/manage/soldLivestock_tab");
		return model;
	}
	
}
