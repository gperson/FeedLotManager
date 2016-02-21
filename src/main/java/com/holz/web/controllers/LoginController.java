package com.holz.web.controllers;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.holz.web.models.User;
import com.holz.web.services.UserServices;

@Controller
public class LoginController {

	@Autowired
	private UserServices userServices;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "logout", required = false) String logout) {
		ModelAndView model = new ModelAndView("manager.login");
		if (error != null) {
			model.addObject("error", "Invalid username or password.");
		}

		if (logout != null) {
			model.addObject("msg", "Logged out successfully.");
		}
		return model;
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {

		ModelAndView model = new ModelAndView("manager.noAccess");

		//check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			model.addObject("username", userDetail.getUsername());			
		} 
		return model;

	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
	public ModelAndView resetPassword() {
		ModelAndView model = new ModelAndView("manager.resetPassword","command",new User());
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public ModelAndView resetPassword(@ModelAttribute("user") User user, BindingResult result) {		
		ModelAndView model = new ModelAndView("manager.resetPassword","command",new User());
		this.userServices.resetPassword(user.getEmail(),true); 
		model.addObject("msg", "Reset instructions sent.");
		return model;
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public ModelAndView changePassword() {
		ModelAndView model = new ModelAndView("manager.changePassword");
		return model;
	}
	
	@ResponseBody
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public ModelAndView changePassword(@RequestParam("password1") String password1,@RequestParam("password2") String password2, Principal principal) {		
		boolean success = this.userServices.changePassword(principal.getName(), password1, password2);
		if(!success){
			ModelAndView model = new ModelAndView("manager.changePassword");
			model.addObject("error", "Passwords didn't match.");
			return model;
		}
		return new ModelAndView("manager.home");
	}
}