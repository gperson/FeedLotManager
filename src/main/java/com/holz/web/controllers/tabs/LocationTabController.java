package com.holz.web.controllers.tabs;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.holz.web.models.GroupedHerd;
import com.holz.web.models.GroupedHerdUpdate;
import com.holz.web.models.Locale;
import com.holz.web.models.enums.FarmLoadOption;
import com.holz.web.models.responses.AjaxResponse;
import com.holz.web.services.FarmServices;
import com.holz.web.services.GroupedHerdServices;
import com.holz.web.services.HerdServices;
import com.holz.web.services.LocaleServices;

@Controller
public class LocationTabController {
	
	@Autowired 
	LocaleServices localeServices;
	
	@Autowired 
	FarmServices farmServices;
	
	@Autowired
	HerdServices herdServices;
	
	@Autowired 
	GroupedHerdServices groupedHerdServices;
	
	@RequestMapping(value = { "/admin/locationsTab" }, method = RequestMethod.GET)
	public ModelAndView LocationsTab(Principal principal) {
		ModelAndView model = new ModelAndView("templates/manage/locations_tab");
		int farmId = this.farmServices.getFarmByUserName(principal.getName(), FarmLoadOption.FARM_NAME_AND_ID).getId();
		List<Locale> l = this.localeServices.getLocalesAndGroupedHerd(farmId);
		model.addObject("locales", l);
		model.addObject("orphans", this.herdServices.getOrphanHerds(farmId));
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value = { "/admin/saveLocale" }, method = RequestMethod.POST)
	public AjaxResponse SaveLocale(@RequestBody Locale locale, Principal principal) {
		int farmId = this.farmServices.getFarmByUserName(principal.getName(), FarmLoadOption.FARM_NAME_AND_ID).getId();		
		this.localeServices.saveOrUpdateLocale(locale, farmId);
		return new AjaxResponse(true);
	}
	
	@ResponseBody
	@RequestMapping(value = { "/admin/saveGroupedHerd" }, method = RequestMethod.POST)
	public AjaxResponse SaveGroupedHerd(@RequestBody GroupedHerdUpdate groupedHerdUpdate, Principal principal) {
		int farmId = this.farmServices.getFarmByUserName(principal.getName(), FarmLoadOption.FARM_NAME_AND_ID).getId();				
		
		Locale locale = new Locale();
		locale.setId(groupedHerdUpdate.getLocaleId());
		if(this.localeServices.hasAccessToLocale(farmId,locale)){
			this.groupedHerdServices.saveOrUpdateGroupedHerd(groupedHerdUpdate,farmId);
		}
		return new AjaxResponse(true);
	}
}
