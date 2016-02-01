package com.holz.web.controllers.tabs;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.holz.web.models.Packer;
import com.holz.web.models.responses.AjaxResponse;
import com.holz.web.services.FarmServices;
import com.holz.web.services.PackerServices;

@Controller
public class PackersTabController {
	
	@Autowired 
	PackerServices packerService;
	
	@Autowired 
	FarmServices farmServices;
	
	@RequestMapping(value = { "/admin/packersTab" }, method = RequestMethod.GET)
	public ModelAndView PackersTab(Principal principal) {
		ModelAndView model = new ModelAndView("templates/manage/packers_tab");
		int farmId = this.farmServices.getFarmByUserName(principal.getName()).getId();		
		model.addObject("packers", this.packerService.getPackers(farmId));
		return model;
	}

	@ResponseBody
	@RequestMapping(value = { "/admin/savePacker" }, method = RequestMethod.POST)
	public AjaxResponse SavePacker(@RequestBody Packer packer, Principal principal) {
		int farmId = this.farmServices.getFarmByUserName(principal.getName()).getId();		
		this.packerService.saveOrUpdatePacker(packer, farmId);
		return new AjaxResponse(true);
	}
}
