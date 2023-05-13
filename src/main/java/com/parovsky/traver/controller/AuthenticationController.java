package com.parovsky.traver.controller;

import com.parovsky.traver.dto.model.UserModel;
import com.parovsky.traver.dto.view.UserView;
import com.parovsky.traver.exception.impl.UserIsAlreadyExistException;
import com.parovsky.traver.exception.impl.UserNotFoundException;
import com.parovsky.traver.exception.impl.VerificationCodeNotMatchException;
import com.parovsky.traver.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor(onConstructor = @__({@org.springframework.beans.factory.annotation.Autowired}))
public class AuthenticationController {

	private final UserService userService;

	@PostMapping("/sign-in")
	public ResponseEntity<UserView> authenticateUser(@RequestBody UserModel loginRequest) {
		return userService.authenticateUser(loginRequest);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/sign-up", consumes = "application/json")
	public UserView saveUser(@RequestBody UserModel userModel) throws UserIsAlreadyExistException {
		return userService.saveUser(userModel);
	}

	@PostMapping("/sign-out")
	public ResponseEntity<Void> logoutUser() {
		return userService.logoutUser();
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PostMapping(value = "/send-verification-code", consumes = "application/json")
	public void verifyEmail(@RequestBody UserModel userModel) throws UserNotFoundException {
		userService.sendVerificationEmail(userModel);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PostMapping(value = "/check-verification-code", consumes = "application/json")
	public void checkVerificationCode(@RequestBody UserModel userModel) throws UserNotFoundException, VerificationCodeNotMatchException {
		userService.checkVerificationCode(userModel);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping(value = "/reset-password", consumes = "application/json")
	public void resetPassword(@RequestBody UserModel userModel) throws UserNotFoundException, VerificationCodeNotMatchException {
		userService.resetPassword(userModel);
	}

}
