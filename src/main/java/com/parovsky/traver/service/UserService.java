package com.parovsky.traver.service;

import com.parovsky.traver.dto.model.*;
import com.parovsky.traver.dto.view.UserView;
import com.parovsky.traver.exception.EntityAlreadyExistsException;
import com.parovsky.traver.exception.EntityNotFoundException;
import com.parovsky.traver.exception.VerificationCodeNotMatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;

import javax.validation.Valid;
import java.util.List;

public interface UserService {

	List<UserView> getAllUsers();

	UserView getUserById(@NonNull Long id) throws EntityNotFoundException;

	UserView getCurrentUser() throws EntityNotFoundException;

	ResponseEntity<UserView> authenticateUser(@Valid @NonNull SignInModel model);

	ResponseEntity<Void> logoutUser();

	void sendVerificationEmail(@Valid @NonNull SendVerificationCodeModel model) throws EntityNotFoundException;

	void checkVerificationCode(@Valid @NonNull CheckVerificationCodeModel model) throws EntityNotFoundException, VerificationCodeNotMatchException;

	UserView saveUser(@Valid @NonNull SignUpModel model) throws EntityAlreadyExistsException;

	UserView saveUserByAdmin(@Valid @NonNull SaveUserModel model) throws EntityAlreadyExistsException;

	UserView updateUser(@Valid @NonNull UpdateUserModel model) throws EntityNotFoundException;

	void deleteUser(@NonNull Long id) throws EntityNotFoundException;

	void resetPassword(@Valid @NonNull ResetPasswordModel model) throws EntityNotFoundException, VerificationCodeNotMatchException;
}
