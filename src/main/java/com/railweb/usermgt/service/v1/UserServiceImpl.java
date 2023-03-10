package com.railweb.usermgt.service.v1;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;

import com.railweb.exception.EntityType;
import com.railweb.exception.ExceptionType;
import com.railweb.exception.RailException;
import com.railweb.usermgt.dto.UserDTO;
import com.railweb.usermgt.exception.UserDTONotValidException;
import com.railweb.usermgt.exception.UserNotFoundException;
import com.railweb.usermgt.model.Role;
import com.railweb.usermgt.model.User;
import com.railweb.usermgt.model.ids.UserId;
import com.railweb.usermgt.repo.RoleRepository;
import com.railweb.usermgt.repo.UserRepository;
import com.railweb.usermgt.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service("userService")
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final BCryptPasswordEncoder passwordEncoder;
	private final UserRepository userRepo;
	
	private RoleRepository roleRepo;
	
	private ModelMapper modelMapper;
	
	private JavaMailSender mailSender;

	private TemplateEngine htmlTemplateEngine;

	@Override
	public List<UserDTO> listAllUsers() {
		List<User> users = userRepo.findAll();
		return users.stream().map(user->modelMapper.map(user, UserDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public UserDTO findByUsername(String username) {
		Optional<User> user = Optional.ofNullable(userRepo.findUserByUsername(username));
		if(user.isPresent()) {
			return modelMapper.map(user.get(), UserDTO.class);
		}
		throw exception(ExceptionType.ENTITY_NOT_FOUND, username);
	}

	@Override
	public UserDTO addUser(UserDTO userDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO updateProfile(UserDTO userDTO) throws UserDTONotValidException, UserNotFoundException {
		if(userDTO.getId() == null) throw new UserDTONotValidException("No Id in the recieved userDTO");
		Optional<User> user = userRepo.findById(new UserId(userDTO.getId()));
		if(user.isPresent()) {
			User usermodel = user.get();
			usermodel.setEmail(userDTO.getEmail());
			usermodel.setFirstname(userDTO.getFirstname());
			usermodel.setLastname(userDTO.getLastname());
			usermodel.setPassword(userDTO.getPassword());
			usermodel.setPhoto(userDTO.getPhoto());
			for(Long id:userDTO.getRoles()) {
				Optional<Role> role = roleRepo.findById(id);
				if(role.isPresent()){
					usermodel.addRole(role.get());
				}
			}
			return convertToDto(userRepo.save(usermodel));

		}throw new UserNotFoundException("No User for id:" + userDTO.getId());
	}

	@Override
	public UserDTO changePassword(UserDTO userDTO, String changePassword) {
		Optional<User> user = Optional.ofNullable(userRepo.findUserByUsername(userDTO.getUsername()));
		if(user.isPresent()) {
			User userModel = user.get();
			userModel.setPassword(passwordEncoder.encode(changePassword));
		}
		throw exception(ExceptionType.ENTITY_NOT_FOUND, userDTO.getUsername());
	}

	@Override
	public UserDTO copyUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private RuntimeException exception(EntityType entityType, ExceptionType exceptionType, String...args) {
		return RailException.throwException(entityType,exceptionType, args);
	}
	private RuntimeException exception(ExceptionType exceptionType, String...args) {
		return RailException.throwException(EntityType.USER,exceptionType, args);
	}

	@Override
	public UserDTO getByResetPasswordToken(String token) {
		Optional<User> user = Optional.ofNullable(userRepo.findByResetPasswordToken(token));
		if(user.isPresent()) {
			return modelMapper.map(user, UserDTO.class);
		}
		return null;
	}

	@Override
	public void updateResetPasswordToken(String token, String username) {
		User user = userRepo.findUserByUsername(username);
		if(user != null) {
			user.setResetPasswordToken(token);
			userRepo.save(user);
		}else {
			exception(EntityType.USER, ExceptionType.ENTITY_NOT_FOUND, username);
		}
		
	}

	@Override
	public void updatePassword(UserDTO userDTO, String newPassword) throws UserDTONotValidException {
		String encodedPassword = passwordEncoder.encode(newPassword);
		User user;
		if(userDTO.getId() == null && userDTO.getUsername() == null) {
			throw new UserDTONotValidException("No Id or email in the recieved userDTO");
		}else if(userDTO.getId() != null) {
			user = userRepo.getById(new UserId(userDTO.getId()));
		}else {
			user = userRepo.findUserByUsername(userDTO.getUsername());
		}
		user.setPassword(encodedPassword);
		user.setResetPasswordToken(null);
		userRepo.save(user);
	}
	
	private UserDTO convertToDto(User user) {
		UserDTO userDTO = modelMapper.map(user, UserDTO.class);
		userDTO.setRoles(user.getRoles().stream().map(role->role.getId().getId())
				.collect(Collectors.toSet()));
		return userDTO;
	}
	private User convertToModel(UserDTO userDTO){
		User user = modelMapper.map(userDTO, User.class);
		Set<Role> roles = new HashSet<>();
		for(Long roleId : userDTO.getRoles()) {
			Role role = roleRepo.getById(roleId);
			roles.add(role);
		}
		user.setRoles(roles);
		return user;
	}
}
