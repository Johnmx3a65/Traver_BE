package com.parovsky.traver.service.impl;

import com.parovsky.traver.dao.UserDAO;
import com.parovsky.traver.dto.UserDTO;
import com.parovsky.traver.entity.User;
import com.parovsky.traver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

	private final UserDAO userDAO;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UserServiceImpl(UserDAO userDAO) {
		this.userDAO = userDAO;
		this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> users = userDAO.getAllUsers();
		return users.stream().map(UserService::transformUserToUserDTO).collect(Collectors.toList());
	}

	@Override
	public UserDTO getUserById(@NonNull Long id) {
		User user = userDAO.getUserById(id);
		return UserService.transformUserToUserDTO(user);
	}

	@Override
	public UserDTO getUserByEmail(@NonNull String email) {
		User user = userDAO.getUserByEmail(email);
		return UserService.transformUserToUserDTO(user);
	}

	@Override
	public boolean isUserExist(Long id) {
		return userDAO.isUserExist(id);
	}

	@Override
	public boolean isUserExistByEmail(String email) {
		return userDAO.isUserExistByEmail(email);
	}

	@Override
	public UserDTO saveUser(@NonNull UserDTO userDTO) {
		userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
		return UserService.transformUserToUserDTO(userDAO.saveUser(userDTO));
	}

	@Override
	public UserDTO updateUser(@NonNull UserDTO userDTO) {
		return UserService.transformUserToUserDTO(userDAO.updateUser(userDTO));
	}

	@Override
	public void resetPassword(UserDTO userDTO) {
		User user = userDAO.getUserByEmail(userDTO.getMail());
		user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
		userDAO.updateUser(UserService.transformUserToUserDTO(user));
	}

	@Override
	public boolean checkVerificationCode(UserDTO userDTO) {
		User user = userDAO.getUserByEmail(userDTO.getMail());
		return user.getVerifyCode().equals(userDTO.getVerifyCode());
	}

	@Override
	public void deleteUser(@NonNull Long id) {
		userDAO.deleteUser(id);
	}

	public String getCurrentUserEmail() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String userEmail;

		if (principal instanceof UserDetails) {
			userEmail = ((UserDetails) principal).getUsername();
		} else {
			userEmail = principal.toString();
		}

		return userEmail;
	}
}
