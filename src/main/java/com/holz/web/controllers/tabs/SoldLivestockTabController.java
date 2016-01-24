package com.holz.web.controllers.tabs;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.holz.web.models.Sale;
import com.holz.web.models.enums.FarmLoadOption;
import com.holz.web.models.responses.AjaxResponse;
import com.holz.web.services.FarmServices;
import com.holz.web.services.GroupedHerdServices;
import com.holz.web.services.PackerServices;
import com.holz.web.services.SaleServices;

@Controller
public class SoldLivestockTabController {
	
	@Autowired 
	SaleServices saleServices;
	
	@Autowired 
	FarmServices farmServices;
	
	@Autowired
	PackerServices packerServices;
	
	@Autowired
	GroupedHerdServices groupedHerdServices;
	
	@RequestMapping(value = { "/admin/soldLivestockTab" }, method = RequestMethod.GET)
	public ModelAndView SoldLivestockTab(Principal principal) {
		ModelAndView model = new ModelAndView("templates/manage/soldLivestock_tab");
		int farmId = this.farmServices.getFarmByUserName(principal.getName(), FarmLoadOption.FARM_NAME_AND_ID).getId();		
		model.addObject("sales", this.saleServices.getAllSales(farmId));
		model.addObject("groupedHerds", this.groupedHerdServices.getGroupedHerds(farmId));
		model.addObject("packers",this.packerServices.getPackers(farmId));
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value = { "/admin/saveSoldLivestock" }, method = RequestMethod.POST)
	public AjaxResponse SaveSupplier(@RequestBody Sale sale, Principal principal) {
		int farmId = this.farmServices.getFarmByUserName(principal.getName(), FarmLoadOption.FARM_NAME_AND_ID).getId();		
		this.saleServices.saveOrUpdateSale(sale, farmId);
		return new AjaxResponse(true);
	}
	
}
