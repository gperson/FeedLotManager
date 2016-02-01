package com.holz.web.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.holz.web.models.Feeding;
import com.holz.web.models.enums.FarmLoadOption;
import com.holz.web.models.responses.AjaxResponse;
import com.holz.web.services.FarmServices;
import com.holz.web.services.FeedServices;
import com.holz.web.services.FeedTypeServices;
import com.holz.web.services.FeedingServices;
import com.holz.web.services.GroupedHerdServices;
import com.holz.web.services.UserServices;

@Controller
public class FeedingController {

	@Autowired 
	FarmServices farmServices;

	@Autowired 
	FeedingServices feedingServices;
	
	@Autowired 
	FeedServices feedServices;

	@Autowired 
	FeedTypeServices feedTypeServices;

	@Autowired 
	GroupedHerdServices groupedHerdServices;

	@Autowired
	UserServices userServices;

	@RequestMapping(value = { "/feedMix/{groupId}/{feedingId}" }, method = RequestMethod.GET)
	public ModelAndView mixFeed(Principal principal,@PathVariable int groupId, @PathVariable int feedingId) {
		int farmId = this.farmServices.getFarmByUserName(principal.getName(), FarmLoadOption.FARM_NAME_AND_ID).getId();
		if(this.groupedHerdServices.userHasAccessToGroupedHerd(principal.getName(), groupId, farmId)){
			ModelAndView model = new ModelAndView("manager.feedMix");
			model.addObject("feeds", this.feedTypeServices.getEnabledFeedTypes(farmId));
			model.addObject("leftovers",this.feedServices.getLeftoverFeeds(farmId));
			return model;
		} else {
			return new ModelAndView("manager.noAccess");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/feedMixed", method = RequestMethod.POST)
	public AjaxResponse feedMixed(@RequestBody Feeding feeding,Principal principal) {
		int farmId = this.farmServices.getFarmByUserName(principal.getName(), FarmLoadOption.FARM_NAME_AND_ID).getId();
		if(this.feedingServices.userHasAccessToFeeding(farmId,feeding.getId())){			
			//TODO Check to make sure some one didn't use left overs
			this.feedingServices.saveFeedSelections(feeding,farmId);
			return new AjaxResponse(true,feeding);
		} else{
			return new AjaxResponse(false);
		}
	}

	@RequestMapping(value = { "/deliver/{feedingId}" }, method = RequestMethod.GET)
	public ModelAndView deliver(Principal principal, @PathVariable int feedingId) {
		int farmId = this.farmServices.getFarmByUserName(principal.getName(), FarmLoadOption.FARM_NAME_AND_ID).getId();
		if(this.feedingServices.userHasAccessToFeeding(farmId,feedingId)){
			ModelAndView model = new ModelAndView("manager.deliver");
			return model;
		} else {
			return new ModelAndView("manager.noAccess");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/delivered", method = RequestMethod.POST)
	public AjaxResponse delivered(@RequestBody Feeding feeding,Principal principal) {
		int farmId = this.farmServices.getFarmByUserName(principal.getName(), FarmLoadOption.FARM_NAME_AND_ID).getId();
		if(this.feedingServices.userHasAccessToFeeding(farmId,feeding.getId())){
			feeding.setUser(this.userServices.getUser(principal.getName()));
			this.feedingServices.saveOrUpdateFeeding(feeding, farmId);
			return new AjaxResponse(true);
		} else{
			return new AjaxResponse(false);
		}
	}

	@RequestMapping(value = { "/pickLocation" }, method = RequestMethod.GET)
	public ModelAndView pickLocation(Principal principal) {
		ModelAndView model = new ModelAndView("manager.pickLocation");
		int farmId = this.farmServices.getFarmByUserName(principal.getName(), FarmLoadOption.FARM_NAME_AND_ID).getId();	
		model.addObject("groupedHerds",this.groupedHerdServices.getGroupedHerds(farmId));
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/pickedLocation", method = RequestMethod.POST)
	public AjaxResponse pickedLocaion(@RequestBody Feeding feeding,Principal principal) {
		int farmId = this.farmServices.getFarmByUserName(principal.getName(), FarmLoadOption.FARM_NAME_AND_ID).getId();
		if(this.groupedHerdServices.userHasAccessToGroupedHerd(principal.getName(), feeding.getGroupedHerd().getId(), farmId)){
			feeding.setUser(this.userServices.getUser(principal.getName()));
			return new AjaxResponse(true,this.feedingServices.saveOrUpdateFeeding(feeding,farmId));
		} else{
			return new AjaxResponse(false);
		}
	}

	@RequestMapping(value = { "/editFeeding" }, method = RequestMethod.GET)
	public ModelAndView editFeeding(Principal principal) {
		ModelAndView model = new ModelAndView("manager.editFeeding");
		int farmId = this.farmServices.getFarmByUserName(principal.getName(), FarmLoadOption.FARM_NAME_AND_ID).getId();
		model.addObject("feedings", this.feedingServices.getAllFeedings(farmId));
		model.addObject("groupedHerds",this.groupedHerdServices.getGroupedHerds(farmId));
		model.addObject("feeds", this.feedTypeServices.getEnabledFeedTypes(farmId));
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value = "/editedFeeding", method = RequestMethod.POST)
	public AjaxResponse editedFeeding(@RequestBody Feeding feeding,Principal principal) {
		int farmId = this.farmServices.getFarmByUserName(principal.getName(), FarmLoadOption.FARM_NAME_AND_ID).getId();
		if(this.feedingServices.userHasAccessToFeeding(farmId,feeding.getId())){
			feeding.setUser(this.userServices.getUser(principal.getName()));
			this.feedingServices.editedFeeding(feeding,farmId);
			return new AjaxResponse(true);
		} else{
			return new AjaxResponse(false);
		}
	}
}
