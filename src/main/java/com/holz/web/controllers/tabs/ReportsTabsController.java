package com.holz.web.controllers.tabs;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.holz.web.models.responses.AjaxResponse;
import com.holz.web.services.FarmServices;
import com.holz.web.services.FeedingServices;
import com.holz.web.services.ReportsServices;

@Controller
public class ReportsTabsController {
	
	@Autowired
	private FarmServices farmServices;
	
	@Autowired
	private FeedingServices feedingServices;
	
	@Autowired
	private ReportsServices reportsServices;

	@RequestMapping(value = { "/admin/poundsGainedTab" }, method = RequestMethod.GET)
	public ModelAndView UsersTab(Principal principal) {
		ModelAndView model = new ModelAndView("templates/reports/poundsGained_tab");
		int farmId = this.farmServices.getFarmByUserName(principal.getName()).getId();		
		model.addObject("gains", this.reportsServices.getPoundsGainedPerPoundDriedFoods(farmId));
		model.addObject("overviews", this.reportsServices.getSalesOverview(farmId));
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value = { "/admin/feedings/{id}" }, method = RequestMethod.GET)
	public AjaxResponse GetFeedingInfo(Principal principal,@PathVariable int id) {
		AjaxResponse res = new AjaxResponse(true); 
		int farmId = this.farmServices.getFarmByUserName(principal.getName()).getId();		
		res.setObject(this.reportsServices.getFeedingHistory(farmId,id));
		return res;
	}
}
