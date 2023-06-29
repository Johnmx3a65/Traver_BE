package com.parovsky.traver.service;

import com.parovsky.traver.dto.model.ResetPasswordModel;
import com.parovsky.traver.dto.model.UserModel;
import com.parovsky.traver.dto.view.UserView;
import com.parovsky.traver.exception.impl.UserIsAlreadyExistException;
import com.parovsky.traver.exception.impl.UserNotFoundException;
import com.parovsky.traver.exception.impl.VerificationCodeNotMatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;

import java.util.List;

public interface UserService {

	List<UserView> getAllUsers();

	UserView getUserById(@NonNull Long id) throws UserNotFoundException;

	UserView getUserByEmail(String email) throws UserNotFoundException;

	UserView getCurrentUser();

	ResponseEntity<UserView> authenticateUser(@NonNull UserModel userModel);

	ResponseEntity<Void> logoutUser();

	void sendVerificationEmail(@NonNull UserModel userModel) throws UserNotFoundException;

	void checkVerificationCode(@NonNull UserModel userModel) throws UserNotFoundException, VerificationCodeNotMatchException;

	UserView saveUser(@NonNull UserModel userModel) throws UserIsAlreadyExistException;

	UserView saveUserByAdmin(@NonNull UserModel userModel) throws UserIsAlreadyExistException;

	UserView updateUser(@NonNull UserModel userModel) throws UserNotFoundException;

	void deleteUser(@NonNull Long id) throws UserNotFoundException;

	void resetPassword(@NonNull ResetPasswordModel resetPasswordModel) throws UserNotFoundException, VerificationCodeNotMatchException;
}
