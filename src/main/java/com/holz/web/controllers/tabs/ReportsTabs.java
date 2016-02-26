package com.holz.web.controllers.tabs;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.holz.web.services.FarmServices;
import com.holz.web.services.GroupedHerdServices;

@Controller
public class ReportsTabs {
	
	@Autowired
	private FarmServices farmServices;
	
	@Autowired
	private GroupedHerdServices groupedHerdServices;

	@RequestMapping(value = { "/admin/poundsGainedTab" }, method = RequestMethod.GET)
	public ModelAndView UsersTab(Principal principal) {
		ModelAndView model = new ModelAndView("templates/reports/poundsGained_tab");
		int farmId = this.farmServices.getFarmByUserName(principal.getName()).getId();		
		model.addObject("groups", this.groupedHerdServices.getGroupedHerds(farmId));
		return model;
	}
}
