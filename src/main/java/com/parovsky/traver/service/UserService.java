package com.parovsky.traver.service;

import com.parovsky.traver.dto.UserDTO;
import com.parovsky.traver.entity.User;
import org.springframework.lang.NonNull;

import java.util.List;

public interface UserService {

	List<UserDTO> getAllUsers();

	UserDTO getUserById(Long id);

	UserDTO getUserByEmail(String email);

	String getCurrentUserEmail();

	boolean isUserExist(Long id);

	boolean isUserExistByEmail(String email);

	UserDTO saveUser(@NonNull UserDTO userDTO);

	UserDTO updateUser(@NonNull UserDTO userDTO);

	void deleteUser(Long id);

	void resetPassword(UserDTO userDTO);

	boolean checkVerificationCode(UserDTO userDTO);

	static UserDTO transformUserToUserDTO(User user) {
		return new UserDTO(
				user.getId(),
				user.getName(),
				user.getEmail(),
				user.getPassword(),
				user.getRole(),
				user.getVerifyCode()
		);
	}
}
