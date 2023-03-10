package com.railweb.usermgt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.railweb.usermgt.dto.UserDTO;
import com.railweb.usermgt.exception.UserNotFoundException;
import com.railweb.usermgt.service.UserService;

@Controller
public class AppController {

	@Autowired
	private UserService userService;
	
	@GetMapping(value={"/","/index"})
	public String viewHomePage() {
		return "index";
	}
	
	@ModelAttribute("loggedinUser")
	public UserDTO globalUser() throws UserNotFoundException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null) {
			UserDTO userDTO = userService.findByUsername(authentication.getName());
			if(userDTO !=null) {
				return userDTO;
			}
		}
		return null;
	}
}