package com.parovsky.traver.service;

import com.parovsky.traver.dto.model.UserModel;
import com.parovsky.traver.dto.view.UserView;
import com.parovsky.traver.exception.EntityAlreadyExistsException;
import com.parovsky.traver.exception.EntityNotFoundException;
import com.parovsky.traver.exception.UnprocessableEntityException;
import com.parovsky.traver.exception.VerificationCodeNotMatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;

import java.util.List;

public interface UserService {

	List<UserView> getAllUsers();

	UserView getUserById(@NonNull Long id) throws EntityNotFoundException;

	UserView getCurrentUser() throws EntityNotFoundException;

	ResponseEntity<UserView> authenticateUser(@NonNull UserModel userModel) throws UnprocessableEntityException;

	ResponseEntity<Void> logoutUser();

	void sendVerificationEmail(@NonNull UserModel userModel) throws EntityNotFoundException;

	void checkVerificationCode(@NonNull UserModel userModel) throws EntityNotFoundException, VerificationCodeNotMatchException, UnprocessableEntityException;

	UserView saveUser(@NonNull UserModel userModel) throws EntityAlreadyExistsException, UnprocessableEntityException;

	UserView saveUserByAdmin(@NonNull UserModel userModel) throws EntityAlreadyExistsException;

	UserView updateUser(@NonNull UserModel userModel) throws EntityNotFoundException, UnprocessableEntityException;

	void deleteUser(@NonNull Long id) throws EntityNotFoundException;

	void resetPassword(@NonNull UserModel userModel) throws EntityNotFoundException, VerificationCodeNotMatchException, UnprocessableEntityException;
}
