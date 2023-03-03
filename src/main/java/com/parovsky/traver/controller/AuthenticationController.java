package com.parovsky.traver.controller;

import com.parovsky.traver.config.UserPrincipal;
import com.parovsky.traver.dto.UserDTO;
import com.parovsky.traver.exception.impl.UserIsAlreadyExistException;
import com.parovsky.traver.exception.impl.UserNotFoundException;
import com.parovsky.traver.exception.impl.VerificationCodeNotMatchException;
import com.parovsky.traver.security.jwt.JwtUtils;
import com.parovsky.traver.service.EmailService;
import com.parovsky.traver.service.UserService;
import com.parovsky.traver.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

	private final AuthenticationManager authenticationManager;

	private final JwtUtils jwtUtils;

	private final UserService userService;

	private final EmailService emailService;

	@Autowired
	public AuthenticationController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserService userService, EmailService emailService) {
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
		this.userService = userService;
		this.emailService = emailService;
	}

	@PostMapping("/signin")
	public ResponseEntity<UserDTO> authenticateUser(@RequestBody UserDTO loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getMail(),
						loginRequest.getPassword()
				)
		);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();

		ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

		List<String> roles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(
				new UserDTO(
						userDetails.getId(),
						userDetails.getUsername(),
						userDetails.getName(),
						roles.get(0)
				)
		);
	}

	@PostMapping(value = "/signup", consumes = "application/json")
	public ResponseEntity<UserDTO> saveUser(@RequestBody UserDTO userDTO) throws UserIsAlreadyExistException {
		if (userService.isUserExistByEmail(userDTO.getMail())) {
			throw new UserIsAlreadyExistException();
		}
		UserDTO user = userService.saveUser(userDTO);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PostMapping("/signout")
	public ResponseEntity<Void> logoutUser() {
		ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
	}

	@PostMapping(value = "/send-verification-code", consumes = "application/json")
	public ResponseEntity<Void> verifyEmail(@RequestBody UserDTO userDTO) throws UserNotFoundException {
		if (!userService.isUserExistByEmail(userDTO.getMail())) {
			throw new UserNotFoundException();
		}
		int code = Utils.generateVerificationCode();
		emailService.sendEmail(userDTO.getMail(), "Verification code", "Your verification code is: " + code);
		UserDTO user = userService.getUserByEmail(userDTO.getMail());
		user.setVerifyCode(String.valueOf(code));
		userService.updateUser(user);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PostMapping(value = "/check-verification-code", consumes = "application/json")
	public ResponseEntity<Void> checkVerificationCode(@RequestBody UserDTO userDTO) throws UserNotFoundException, VerificationCodeNotMatchException {
		if (userService.isUserExistByEmail(userDTO.getMail())) {
			if (!userService.checkVerificationCode(userDTO)) {
				throw new VerificationCodeNotMatchException();
			}
		} else {
			throw new UserNotFoundException();
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PutMapping(value = "/reset-password", consumes = "application/json")
	public ResponseEntity<Void> resetPassword(@RequestBody UserDTO userDTO) throws UserNotFoundException, VerificationCodeNotMatchException {
		if (!userService.isUserExistByEmail(userDTO.getMail())) {
			throw new UserNotFoundException();
		}
		if (!userService.checkVerificationCode(userDTO)) {
			throw new VerificationCodeNotMatchException();
		}
		userService.resetPassword(userDTO);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
