package com.parovsky.traver.service;

import com.parovsky.traver.dto.UserModel;
import com.parovsky.traver.dto.view.UserView;
import com.parovsky.traver.entity.User;
import com.parovsky.traver.exception.impl.UserIsAlreadyExistException;
import com.parovsky.traver.exception.impl.UserNotFoundException;
import org.springframework.lang.NonNull;

import java.util.List;

public interface UserService {

	List<UserView> getAllUsers();

	UserView getUserById(Long id) throws UserNotFoundException;

	UserView getUserByEmail(String email) throws UserNotFoundException;

	UserView getCurrentUser();

	boolean isUserExistByEmail(String email);

	UserView saveUser(@NonNull UserModel userModel) throws UserIsAlreadyExistException;

	UserView updateUser(@NonNull UserModel userModel) throws UserNotFoundException;

	void deleteUser(Long id) throws UserNotFoundException;

	void resetPassword(UserModel userModel);

	boolean checkVerificationCode(UserModel userModel);

	static UserModel transformUserToUserDTO(User user) {
		return new UserModel(
				user.getId(),
				user.getName(),
				user.getEmail(),
				user.getPassword(),
				user.getRole(),
				user.getVerifyCode()
		);
	}
}
