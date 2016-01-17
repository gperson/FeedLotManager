package com.holz.web.controllers.tabs;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.holz.web.models.Feed;
import com.holz.web.models.enums.FarmLoadOption;
import com.holz.web.models.responses.AjaxResponse;
import com.holz.web.services.FarmServices;
import com.holz.web.services.FeedServices;

@Controller
public class FeedTabController {
	
	@Autowired 
	FeedServices feedServices;
	
	@Autowired 
	FarmServices farmServices;
	
	@RequestMapping(value = { "/admin/feedTab" }, method = RequestMethod.GET)
	public ModelAndView FeedTab(Principal principal) {
		ModelAndView model = new ModelAndView("templates/manage/feed_tab");
		int farmId = this.farmServices.getFarmByUserName(principal.getName(), FarmLoadOption.FARM_NAME_AND_ID).getId();	
		model.addObject("feeds", this.feedServices.getFeeds(farmId));
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value = { "/admin/saveFeed" }, method = RequestMethod.POST)
	public AjaxResponse SaveFeed(@RequestBody Feed feed, Principal principal) {
		int farmId = this.farmServices.getFarmByUserName(principal.getName(), FarmLoadOption.FARM_NAME_AND_ID).getId();		
		this.feedServices.saveOrUpdateFeed(feed, farmId);
		return new AjaxResponse(true);
	}
	
	@ResponseBody
	@RequestMapping(value = { "/admin/enableDisableFeed" }, method = RequestMethod.POST)
	public AjaxResponse EnableDisableFeedFeed(@RequestBody Feed feed, Principal principal) {
		int farmId = this.farmServices.getFarmByUserName(principal.getName(), FarmLoadOption.FARM_NAME_AND_ID).getId();		
		this.feedServices.enableDisableFeed(feed, farmId);
		return new AjaxResponse(true);
	}
}
