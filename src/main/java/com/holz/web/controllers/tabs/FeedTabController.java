package com.holz.web.controllers.tabs;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.holz.web.models.FeedType;
import com.holz.web.models.responses.AjaxResponse;
import com.holz.web.services.FarmServices;
import com.holz.web.services.FeedTypeServices;

@Controller
public class FeedTabController {
	
	@Autowired 
	FeedTypeServices feedServices;
	
	@Autowired 
	FarmServices farmServices;
	
	@RequestMapping(value = { "/admin/feedTab" }, method = RequestMethod.GET)
	public ModelAndView FeedTab(Principal principal) {
		ModelAndView model = new ModelAndView("templates/manage/feed_tab");
		int farmId = this.farmServices.getFarmByUserName(principal.getName()).getId();	
		model.addObject("feeds", this.feedServices.getFeedTypes(farmId));
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value = { "/admin/saveFeed" }, method = RequestMethod.POST)
	public AjaxResponse SaveFeed(@RequestBody FeedType feed, Principal principal) {
		int farmId = this.farmServices.getFarmByUserName(principal.getName()).getId();		
		this.feedServices.saveOrUpdateFeedType(feed, farmId);
		return new AjaxResponse(true);
	}
	
	@ResponseBody
	@RequestMapping(value = { "/admin/enableDisableFeed" }, method = RequestMethod.POST)
	public AjaxResponse EnableDisableFeedFeed(@RequestBody FeedType feed, Principal principal) {
		int farmId = this.farmServices.getFarmByUserName(principal.getName()).getId();		
		this.feedServices.enableDisableFeedType(feed, farmId);
		return new AjaxResponse(true);
	}
}
