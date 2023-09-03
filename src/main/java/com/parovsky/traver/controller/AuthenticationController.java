package com.parovsky.traver.controller;

import com.parovsky.traver.dto.form.*;
import com.parovsky.traver.dto.response.UserResponse;
import com.parovsky.traver.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor(onConstructor = @__({@org.springframework.beans.factory.annotation.Autowired}))
public class AuthenticationController {

	private final UserService userService;

	@PostMapping(value = "/sign-in", consumes = "application/json")
	public ResponseEntity<UserResponse> authenticateUser(@Valid @RequestBody SignInForm model) {
		return userService.authenticateUser(model);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/sign-up", consumes = "application/json")
	public UserResponse saveUser(@Valid @RequestBody SignUpForm model) {
		return userService.saveUser(model);
	}

	@PostMapping("/sign-out")
	public ResponseEntity<Void> logoutUser() {
		return userService.logoutUser();
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PostMapping(value = "/send-verification-code", consumes = "application/json")
	public void verifyEmail(@Valid @RequestBody SendVerificationCodeForm model) {
		userService.sendVerificationEmail(model);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PostMapping(value = "/check-verification-code", consumes = "application/json")
	public void checkVerificationCode(@Valid @RequestBody CheckVerificationCodeForm model) {
		userService.checkVerificationCode(model);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping(value = "/reset-password", consumes = "application/json")
	public void resetPassword(@Valid @RequestBody ResetPasswordForm model) {
		userService.resetPassword(model);
	}

}
