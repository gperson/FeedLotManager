package com.holz.web.controllers.tabs;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.holz.web.models.Supplier;
import com.holz.web.models.responses.AjaxResponse;
import com.holz.web.services.FarmServices;
import com.holz.web.services.SupplierServices;

@Controller
public class SuppliersTabController {
	
	@Autowired 
	SupplierServices supplierServices;
	
	@Autowired 
	FarmServices farmServices;
	
	@RequestMapping(value = { "/admin/suppliersTab" }, method = RequestMethod.GET)
	public ModelAndView SuppliersTab(Principal principal) {
		ModelAndView model = new ModelAndView("templates/manage/suppliers_tab");
		int farmId = this.farmServices.getFarmByUserName(principal.getName()).getId();		
		model.addObject("suppliers", this.supplierServices.getSuppliers(farmId));
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value = { "/admin/saveSupplier" }, method = RequestMethod.POST)
	public AjaxResponse SaveSupplier(@RequestBody Supplier supplier, Principal principal) {
		int farmId = this.farmServices.getFarmByUserName(principal.getName()).getId();		
		this.supplierServices.saveOrUpdateSupplier(supplier, farmId);
		return new AjaxResponse(true);
	}
	
}
