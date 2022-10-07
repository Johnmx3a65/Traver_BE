package com.parovsky.traver.dao.impl;

import com.parovsky.traver.dao.UserDAO;
import com.parovsky.traver.dto.UserDTO;
import com.parovsky.traver.entity.User;
import com.parovsky.traver.repository.UserRepository;
import com.parovsky.traver.role.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class UserDAOImpl implements UserDAO {

	private final UserRepository userRepository;

	private final BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserDAOImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
		this.passwordEncoder = new BCryptPasswordEncoder();
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User getUserById(Long id) {
		return userRepository.getById(id);
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.getByEmail(email);
	}

	@Override
	public boolean isUserExist(Long id) {
		return userRepository.existsById(id);
	}

	@Override
	public boolean isUserExistByEmail(String email) {
		return userRepository.existsUserByEmail(email);
	}

	@Override
	public User saveUser(@NonNull UserDTO userDTO) {
		User user = new User();
		user.setEmail(userDTO.getMail());
		user.setName(userDTO.getName());
		user.setPassword(userDTO.getPassword());
		user.setRole(userDTO.getRole().equals(Role.ADMIN.name()) ? Role.ADMIN.name() : Role.USER.name());
		return userRepository.saveAndFlush(user);
	}

	@Override
	public User updateUser(@NonNull UserDTO userDTO) {
		User user = userRepository.getById(userDTO.getId());
		user.setEmail(userDTO.getMail());
		user.setName(userDTO.getName());
		user.setRole(userDTO.getRole());
		if (!userDTO.getPassword().isEmpty()) {
			user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		}
		if (userDTO.getVerifyCode() != null && !userDTO.getVerifyCode().isEmpty()) {
			user.setVerifyCode(userDTO.getVerifyCode());
		}
		return userRepository.saveAndFlush(user);
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
}
