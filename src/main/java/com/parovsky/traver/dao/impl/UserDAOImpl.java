package com.parovsky.traver.dao.impl;

import com.parovsky.traver.dao.UserDAO;
import com.parovsky.traver.dto.model.UserModel;
import com.parovsky.traver.entity.User;
import com.parovsky.traver.repository.UserRepository;
import com.parovsky.traver.role.Role;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
@AllArgsConstructor(onConstructor = @__({@org.springframework.beans.factory.annotation.Autowired}))
public class UserDAOImpl implements UserDAO {

	private final UserRepository userRepository;

	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	@Nullable
	public User getUserById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	@Nullable
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email).orElse(null);
	}

	@Override
	@Nullable
	public User getCurrentUser() {
		String currentUserEmail = getCurrentUserEmail();
		return getUserByEmail(currentUserEmail);
	}

	@Override
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

	@Override
	public boolean isUserExist(Long id) {
		return userRepository.existsById(id);
	}

	@Override
	public boolean isUserExistByEmail(String email) {
		return userRepository.existsUserByEmail(email);
	}

	@Override
	public User saveUser(@NonNull UserModel userModel) {
		User user = new User();
		user.setEmail(userModel.getEmail());
		user.setName(userModel.getName());
		user.setPassword(passwordEncoder.encode(userModel.getPassword()));
		user.setRole(userModel.getRole().equals(Role.ADMIN.name()) ? Role.ADMIN.name() : Role.USER.name());
		return userRepository.saveAndFlush(user);
	}

	@Override
	public User updateUser(@NonNull UserModel userModel) {
		User user = userRepository.getById(userModel.getId());
		user.setEmail(userModel.getEmail());
		user.setName(userModel.getName());
		user.setRole(userModel.getRole());
		if (userModel.getPassword() != null && !userModel.getPassword().isEmpty()) {
			user.setPassword(passwordEncoder.encode(userModel.getPassword()));
		}
		if (userModel.getVerifyCode() != null && !userModel.getVerifyCode().isEmpty()) {
			user.setVerifyCode(userModel.getVerifyCode());
		}
		return userRepository.saveAndFlush(user);
	}

	@Override
	public void updatePassword(@NonNull UserModel userModel) {
		User user = userRepository.getByEmail(userModel.getEmail());
		user.setPassword(passwordEncoder.encode(userModel.getPassword()));
		userRepository.saveAndFlush(user);
	}

	@Override
	public void updateVerificationCode(@NonNull UserModel userModel) {
		User user = userRepository.getByEmail(userModel.getEmail());
		user.setVerifyCode(userModel.getVerifyCode());
		userRepository.saveAndFlush(user);
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
}
