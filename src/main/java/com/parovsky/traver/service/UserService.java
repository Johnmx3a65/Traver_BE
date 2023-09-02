package com.parovsky.traver.service;

import com.parovsky.traver.dto.model.*;
import com.parovsky.traver.dto.view.UserView;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;

import javax.validation.Valid;
import java.util.List;

public interface UserService {

	List<UserView> getAllUsers();

	UserView getUserById(@NonNull Long id);

	UserView getCurrentUser();

	ResponseEntity<UserView> authenticateUser(@Valid @NonNull SignInModel model);

	ResponseEntity<Void> logoutUser();

	void sendVerificationEmail(@Valid @NonNull SendVerificationCodeModel model);

	void checkVerificationCode(@Valid @NonNull CheckVerificationCodeModel model);

	UserView saveUser(@Valid @NonNull SignUpModel model);

	UserView saveUserByAdmin(@Valid @NonNull SaveUserModel model);

	UserView updateUser(@Valid @NonNull UpdateUserModel model);

	void deleteUser(@NonNull Long id);

	void resetPassword(@Valid @NonNull ResetPasswordModel model);
}
