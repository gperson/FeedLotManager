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
public class LivestockTabController {
	
	@Autowired 
	SupplierServices supplierServices;
	
	@Autowired 
	FarmServices farmServices;
	
	@RequestMapping(value = { "/admin/livestockTab" }, method = RequestMethod.GET)
	public ModelAndView LivestockTab(Principal principal) {
		ModelAndView model = new ModelAndView("templates/manage/livestock_tab");
		return model;
	}

}
