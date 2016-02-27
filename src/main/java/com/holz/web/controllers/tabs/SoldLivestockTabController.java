package com.holz.web.controllers.tabs;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.holz.web.models.GroupedHerd;
import com.holz.web.models.Sale;
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
		int farmId = this.farmServices.getFarmByUserName(principal.getName()).getId();		
		model.addObject("sales", this.saleServices.getAllSales(farmId));
		model.addObject("groupedHerds", this.groupedHerdServices.getGroupedHerds(farmId));
		model.addObject("packers",this.packerServices.getPackers(farmId));
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value = { "/admin/saveSoldLivestock" }, method = RequestMethod.POST)
	public AjaxResponse SaveSupplier(@RequestBody Sale sale, Principal principal) {
		int farmId = this.farmServices.getFarmByUserName(principal.getName()).getId();		
		if(this.saleServices.saveOrUpdateSale(sale, farmId)){
			return new AjaxResponse(true);
		} else {
			return new AjaxResponse(false);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = { "/admin/isValidSale" }, method = RequestMethod.POST)
	public AjaxResponse IsValidSale(@RequestBody Sale sale, Principal principal) {
		int farmId = this.farmServices.getFarmByUserName(principal.getName()).getId();		
		GroupedHerd gh = this.groupedHerdServices.getGroupedHerd(farmId,sale.getGroupedHerd().getId());
		
		//Incase we are updating the quantity we need set the new quantity before 'getCount'
		int oldSaleCount = 0;
		if(sale.getId() != 0){
			for(Sale s : gh.getSales()){
				if(s.getId() == sale.getId()){
					oldSaleCount = s.getQuantity();
					break;
				}
			}
		}
		
		if((gh.getCount()+oldSaleCount) == sale.getQuantity()){
			return new AjaxResponse(true, "<p>Warning, this will result in all livestock being sold. This action cannot be undone. "
					+ "After it is marked as sold the following take place:</p><ul>"
					+ "<li>You will not be able to edit feedings that were giving to these livestock</li>"
					+ "<li>Location is now marked as empty</li>"
					+ "<li>Some fields will become uneditable</li>"
					+ "<li>Will display as yellow in livestock table</li><ul>");
		} else if((gh.getCount()+oldSaleCount) < sale.getQuantity()){
			return new AjaxResponse(false,"<p>You cannot sell "+sale.getQuantity()+" livestock you only have "+gh.getCount()+".</p>");
		} else {
			return new AjaxResponse(true);
		}
	}
}
