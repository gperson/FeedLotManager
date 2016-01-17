package com.holz.web.controllers.tabs;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.holz.web.models.Herd;
import com.holz.web.models.enums.FarmLoadOption;
import com.holz.web.models.responses.AjaxResponse;
import com.holz.web.services.FarmServices;
import com.holz.web.services.HerdServices;
import com.holz.web.services.SupplierServices;

@Controller
public class LivestockTabController {
	
	@Autowired 
	HerdServices herdServices;
	
	@Autowired
	SupplierServices supplierServices;
	
	@Autowired 
	FarmServices farmServices;
	
	@RequestMapping(value = { "/admin/livestockTab" }, method = RequestMethod.GET)
	public ModelAndView LivestockTab(Principal principal) {
		ModelAndView model = new ModelAndView("templates/manage/livestock_tab");
		int farmId = this.farmServices.getFarmByUserName(principal.getName(), FarmLoadOption.FARM_NAME_AND_ID).getId();		
		model.addObject("herds", this.herdServices.getHerds(farmId));
		model.addObject("suppliers", this.supplierServices.getSuppliers(farmId));
		return model;
	}

	@ResponseBody
	@RequestMapping(value = { "/admin/saveBuyLivestock" }, method = RequestMethod.POST)
	public AjaxResponse SaveFeed(@RequestBody Herd herd, Principal principal) {
		int farmId = this.farmServices.getFarmByUserName(principal.getName(), FarmLoadOption.FARM_NAME_AND_ID).getId();		
		this.herdServices.saveOrUpdateHerd(herd, farmId);
		return new AjaxResponse(true);
	}
}
