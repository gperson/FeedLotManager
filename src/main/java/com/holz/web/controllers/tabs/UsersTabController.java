package com.holz.web.controllers.tabs;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.holz.web.models.User;
import com.holz.web.models.enums.FarmLoadOption;
import com.holz.web.models.responses.AjaxResponse;
import com.holz.web.services.FarmServices;
import com.holz.web.services.UserServices;

@Controller
public class UsersTabController {
	
	@Autowired
	UserServices userServives;
	
	@Autowired 
	FarmServices farmServices;
	
	@RequestMapping(value = { "/admin/usersTab" }, method = RequestMethod.GET)
	public ModelAndView UsersTab(Principal principal) {
		ModelAndView model = new ModelAndView("templates/manage/users_tab");
		int farmId = this.farmServices.getFarmByUserName(principal.getName(), FarmLoadOption.FARM_NAME_AND_ID).getId();		
		model.addObject("users", this.userServives.getUsersForFarm(farmId));
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value = { "/admin/saveUser" }, method = RequestMethod.POST)
	public AjaxResponse SaveUser(@RequestBody User user, Principal principal) {
		int farmId = this.farmServices.getFarmByUserName(principal.getName(), FarmLoadOption.FARM_NAME_AND_ID).getId();		
		this.userServives.saveOrUpdateUser(user, farmId);
		return new AjaxResponse(true);
	}
	
	@ResponseBody
	@RequestMapping(value = { "/admin/resetPassword" }, method = RequestMethod.POST)
	public AjaxResponse UpdatePassword(@RequestBody User user, Principal principal) {
		this.userServives.resetPassword(user.getUsername());
		return new AjaxResponse(true);
	}
	
	@ResponseBody
	@RequestMapping(value = { "/admin/enableDisableUser" }, method = RequestMethod.POST)
	public AjaxResponse EnableDisableUser(@RequestBody User user, Principal principal) {
		int farmId = this.farmServices.getFarmByUserName(principal.getName(), FarmLoadOption.FARM_NAME_AND_ID).getId();		
		this.userServives.enableDisableUser(user, farmId);
		return new AjaxResponse(true);
	}
}
