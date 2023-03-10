package com.railweb.usermgt.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import com.railweb.usermgt.dto.UserDTO;
import com.railweb.usermgt.exception.UserDTONotValidException;
import com.railweb.usermgt.exception.UserNotFoundException;
import com.railweb.usermgt.model.Privilege;
import com.railweb.usermgt.model.Role;
import com.railweb.usermgt.repo.PrivilegeRepository;
import com.railweb.usermgt.repo.RoleRepository;
import com.railweb.usermgt.repo.UserRepository;
import com.railweb.usermgt.service.UserService;
import com.railweb.util.FileUploadUtil;

@Controller
public class UserController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RoleRepository roleRepo;
	@Autowired
	private PrivilegeRepository privRepo;
	@Autowired
	private UserService userService;
	@Autowired
	private ApplicationContext context;
	
	@GetMapping("/users")
	public String listUsers(Model model) {
		List<UserDTO> listUsers = userService.listAllUsers();
		List<Role> listRoles = roleRepo.findAll();
		List<Privilege> listPrivilege = privRepo.findAll();
		model.addAttribute("listUsers", listUsers);
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("listPrivileges", listPrivilege);
		LOGGER.debug("Model:"+ model.toString());

		
		return "admin/users";
	}
	
	@GetMapping("/addUser")
	public String showAddUserForm(Model model) {
		model.addAttribute("user", new UserDTO());
		return "admin/addUser";
	}
	
	@PostMapping("/users/save")
	public RedirectView SaveUser(UserDTO user,
			@RequestParam MultipartFile multipartFile) throws IOException, UserDTONotValidException {
		if(!user.isValid()) throw new UserDTONotValidException();
		String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		user.setPhoto(filename);
		UserDTO savedUser;
		try {
			savedUser = userService.updateProfile(user);
		} catch (UserDTONotValidException | UserNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found", e);
		}
		String uploadDir = "user-photos/" + savedUser.getId();
		FileUploadUtil.saveFile(uploadDir, filename, multipartFile);
		
		return new RedirectView("/users",true);
	}
}
