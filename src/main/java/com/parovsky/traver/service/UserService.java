package com.parovsky.traver.service;

import com.parovsky.traver.dto.form.*;
import com.parovsky.traver.dto.response.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;

import javax.validation.Valid;
import java.util.List;

public interface UserService {

	List<UserResponse> getAllUsers();

	UserResponse getUserById(@NonNull Long id);

	ResponseEntity<UserResponse> authenticateUser(@Valid @NonNull SignInForm model);

	ResponseEntity<Void> logoutUser();

	void sendVerificationEmail(@Valid @NonNull SendVerificationCodeForm model);

	void checkVerificationCode(@Valid @NonNull CheckVerificationCodeForm model);

	UserResponse saveUser(@Valid @NonNull SignUpForm model);

	UserResponse saveUserByAdmin(@Valid @NonNull SaveUserForm model);

	UserResponse updateUser(@Valid @NonNull UpdateUserForm model);

	void deleteUser(@NonNull Long id);

	void resetPassword(@Valid @NonNull ResetPasswordForm model);
}
