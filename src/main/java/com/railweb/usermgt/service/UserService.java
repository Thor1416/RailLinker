package com.railweb.usermgt.service;

import java.util.List;

import com.railweb.usermgt.dto.UserDTO;
import com.railweb.usermgt.exception.UserDTONotValidException;
import com.railweb.usermgt.exception.UserNotFoundException;

public interface UserService {
	
	List<UserDTO> listAllUsers();
	UserDTO findByUsername(String username) throws UserNotFoundException;
	UserDTO addUser(UserDTO userDTO);
	UserDTO updateProfile(UserDTO userDTO) throws UserDTONotValidException, UserNotFoundException;
	UserDTO changePassword(UserDTO userDTO,String changePassword);
	UserDTO copyUser(String username);
	UserDTO getByResetPasswordToken(String token) throws UserNotFoundException;

	void updateResetPasswordToken(String token, String username) throws UserNotFoundException;
	void updatePassword(UserDTO userDTO, String newPassword) throws UserDTONotValidException;
}
