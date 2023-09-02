package com.parovsky.traver.controller;

import com.parovsky.traver.dto.model.*;
import com.parovsky.traver.dto.view.UserView;
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
	public ResponseEntity<UserView> authenticateUser(@Valid @RequestBody SignInModel model) {
		return userService.authenticateUser(model);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/sign-up", consumes = "application/json")
	public UserView saveUser(@Valid @RequestBody SignUpModel model) {
		return userService.saveUser(model);
	}

	@PostMapping("/sign-out")
	public ResponseEntity<Void> logoutUser() {
		return userService.logoutUser();
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PostMapping(value = "/send-verification-code", consumes = "application/json")
	public void verifyEmail(@Valid @RequestBody SendVerificationCodeModel model) {
		userService.sendVerificationEmail(model);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PostMapping(value = "/check-verification-code", consumes = "application/json")
	public void checkVerificationCode(@Valid @RequestBody CheckVerificationCodeModel model) {
		userService.checkVerificationCode(model);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping(value = "/reset-password", consumes = "application/json")
	public void resetPassword(@Valid @RequestBody ResetPasswordModel model) {
		userService.resetPassword(model);
	}

}
