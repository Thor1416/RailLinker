package com.railweb.usermgt.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.railweb.usermgt.dto.UserDTO;
import com.railweb.usermgt.exception.UserNotFoundException;
import com.railweb.usermgt.service.UserService;

@Controller
public class ForgotPasswordController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ForgotPasswordController.class);

	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/forgot_password")
	public String showForgotPassswordForm(){
		return "forgot_password_form";
	}
	@PostMapping("/forgot_password")
	public String processForgotPassword(HttpServletRequest request,Model model) {
		String username = request.getParameter("username");
		String token = RandomString.make(30);
		
		try {
			userService.updateResetPasswordToken(token, username);
			String siteURL =request.getRequestURL().toString();
			siteURL = siteURL.replace(request.getServletPath(),"");
			String resetPasswordLink = siteURL + "/reset_password?token=" + token;
			UserDTO user = userService.findByUsername(username);
			sendEmail(user,resetPasswordLink);
		}catch(UserNotFoundException ex) {
			model.addAttribute("error",ex);
		}catch(UnsupportedEncodingException|MessagingException e) {
			model.addAttribute("error", "Error while sending email");
		}
		
		return "forgot_password_form";
	}
	
	public void sendEmail(UserDTO user, String link)
			throws MessagingException, UnsupportedEncodingException{
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		helper.setFrom("contact@railweb.com", "Railweb Support");
		helper.setTo(user.getEmail());
		
		helper.setSubject("Password reset link");
		
		String greeting;
		if(user.getFirstname() != null) {
			greeting = user.getFirstname();
		}else {
			greeting = user.getUsername();
		}
		
		String content = "<p>Hello," + greeting +  "</p>" 
        + "<p>You have requested to reset your password.</p>"
        + "<p>Click the link below to change your password:</p>"
        + "<p><a href=\"" + link + "\">Change my password</a></p>"
        + "<br>"
        + "<p>Ignore this email if you do remember your password, "
        + "or you have not made the request.</p>";
		
		helper.setText(content, true);
		mailSender.send(message);
	}
	
	@GetMapping("/reset_password")
	public String showResetPasswordForm(@Param(value="token") String token, Model model) 
			throws UserNotFoundException{
		
		UserDTO user = userService.getByResetPasswordToken(token);
		model.addAttribute("token", token);
		
		if(user == null) {
			model.addAttribute("message", "Invalid token");
			return "message";
		}
		return "reset_password_form";
	}
	
	@PostMapping("/reset_password")
	public String processResetPassword(HttpServletRequest request, Model model)
			throws UserNotFoundException{
		
		String token = request.getParameter("token");
		String password = request.getParameter("password");
		
		UserDTO user = userService.getByResetPasswordToken(token);
		model.addAttribute("title", "Reset Your Password");
		
		if(user == null) {
			model.addAttribute("message", "Invalid token");
			return "message";
		}else {
			userService.changePassword(user, password);
			model.addAttribute("message", "You have succesfully changed your password");
			return "message";
		}
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ModelAndView	handleUserNotFound(HttpServletRequest req, UserNotFoundException ex) {
		LOG.error("Requested URL=" + req.getRequestURI());
		LOG.error("Exception Raised=" +ex);
		ModelAndView mav = new ModelAndView();
		mav.addObject("message", "Cannot find a user with this username");
		mav.setViewName("forgot_password_name");
		return mav;
	}
	
}
