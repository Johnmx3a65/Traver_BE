package com.parovsky.traver.service;

import com.parovsky.traver.dto.form.*;
import com.parovsky.traver.dto.view.UserView;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;

import javax.validation.Valid;
import java.util.List;

public interface UserService {

	List<UserView> getAllUsers();

	UserView getUserById(@NonNull Long id);

	ResponseEntity<UserView> authenticateUser(@Valid @NonNull SignInForm model);

	ResponseEntity<Void> logoutUser();

	void sendVerificationEmail(@Valid @NonNull SendVerificationCodeForm model);

	void checkVerificationCode(@Valid @NonNull CheckVerificationCodeForm model);

	UserView saveUser(@Valid @NonNull SignUpForm model);

	UserView saveUserByAdmin(@Valid @NonNull SaveUserForm model);

	UserView updateUser(@Valid @NonNull UpdateUserForm model);

	void deleteUser(@NonNull Long id);

	void resetPassword(@Valid @NonNull ResetPasswordForm model);
}
