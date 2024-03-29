package com.parovsky.traver.service.impl;

import com.parovsky.traver.dto.form.*;
import com.parovsky.traver.dto.view.UserView;
import com.parovsky.traver.entity.Location;
import com.parovsky.traver.entity.User;
import com.parovsky.traver.exception.ApplicationException;
import com.parovsky.traver.repository.UserRepository;
import com.parovsky.traver.security.Role;
import com.parovsky.traver.security.UserPrincipal;
import com.parovsky.traver.security.jwt.JwtUtils;
import com.parovsky.traver.service.EmailService;
import com.parovsky.traver.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.parovsky.traver.exception.Errors.*;
import static com.parovsky.traver.utils.Constraints.EMAIL;
import static com.parovsky.traver.utils.Constraints.ID;
import static com.parovsky.traver.utils.Utils.generateRandomString;
import static com.parovsky.traver.utils.Utils.generateVerificationCode;

@Service
@AllArgsConstructor(onConstructor = @__({@org.springframework.beans.factory.annotation.Autowired}))
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final EmailService emailService;

	private final ModelMapper modelMapper;

	private final AuthenticationManager authenticationManager;

	private final JwtUtils jwtUtils;

	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	public List<UserView> getAllUsers() {
		List<User> users = userRepository.findAll();
		return users.stream().map(user -> modelMapper.map(user, UserView.class)).collect(Collectors.toList());
	}

	@Override
	public UserView getUserById(@NonNull Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ApplicationException(USER_NOT_FOUND, Collections.singletonMap(ID, id)));
		return modelMapper.map(user, UserView.class);
	}

	@Override
	public ResponseEntity<UserView> authenticateUser(@Valid @NonNull SignInForm model) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						model.getEmail(),
						model.getPassword()
				)
		);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();

		ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

		List<String> roles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		UserView userView = UserView
				.builder()
				.id(userDetails.getId())
				.email(userDetails.getUsername())
				.name(userDetails.getName())
				.role(roles.get(0))
				.build();

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(userView);
	}

	@Override
	public ResponseEntity<Void> logoutUser() {
		ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
	}

	@Override
	public void sendVerificationEmail(@Valid @NonNull SendVerificationCodeForm model) {
		User user = userRepository.findByEmail(model.getEmail()).orElseThrow(() -> new ApplicationException(USER_NOT_FOUND_BY_EMAIL, Collections.singletonMap(EMAIL, model.getEmail())));
		int code = generateVerificationCode();
		emailService.sendEmail(user.getEmail(), "Verification code", "Your verification code is: " + code);
		user.setVerifyCode(String.valueOf(code));
		userRepository.saveAndFlush(user);
	}

	@Override
	public void checkVerificationCode(@NonNull CheckVerificationCodeForm model) {
		User user = userRepository.findByEmail(model.getEmail()).orElseThrow(() -> new ApplicationException(USER_NOT_FOUND_BY_EMAIL, Collections.singletonMap(EMAIL, model.getEmail())));
		if (!user.getVerifyCode().equals(model.getVerificationCode())) {
			throw new ApplicationException(VERIFICATION_CODE_NOT_MATCH);
		}
	}

	@Override
	public UserView saveUser(@Valid @NonNull SignUpForm model) {
		if (userRepository.existsByEmail(model.getEmail())) {
			throw new ApplicationException(USER_ALREADY_EXIST, Collections.singletonMap(EMAIL, model.getEmail()));
		}

		int code = generateVerificationCode();
		emailService.sendEmail(model.getEmail(), "TRAVER VERIFICATION CODE", "Your verification code is: " + code);

		User user = User.builder()
				.name(model.getName())
				.email(model.getEmail())
				.role(Role.USER.name())
				.password(passwordEncoder.encode(model.getPassword()))
				.verifyCode(String.valueOf(code))
				.build();
		User newUser = userRepository.saveAndFlush(user);
		return modelMapper.map(newUser, UserView.class);
	}

	@Override
	public UserView saveUserByAdmin(@NonNull @Valid SaveUserForm model) {
		if (userRepository.existsByEmail(model.getEmail())) {
			throw new ApplicationException(USER_ALREADY_EXIST, Collections.singletonMap(EMAIL, model.getEmail()));
		}
		String password = generateRandomString(10);
		emailService.sendEmail(model.getEmail(), "TRAVER CREDENTIALS", "Your password is " + password);

		User user = User.builder()
				.name(model.getName())
				.email(model.getEmail())
				.role(Role.valueOf(model.getRole()).name())
				.password(passwordEncoder.encode(password))
				.build();
		User newUser = userRepository.saveAndFlush(user);
		return modelMapper.map(newUser, UserView.class);
	}

	@Override
	public UserView updateUser(@NonNull @Valid UpdateUserForm model) {
		User user = userRepository.findById(model.getId()).orElseThrow(() -> new ApplicationException(USER_NOT_FOUND, Collections.singletonMap(ID, model.getId())));

		user.setName(model.getName());
		user.setEmail(model.getEmail());
		user.setRole(Role.valueOf(model.getRole()).name());

		User result = userRepository.saveAndFlush(user);
		return modelMapper.map(result, UserView.class);
	}

	@Override
	public void resetPassword(@Valid @NonNull ResetPasswordForm model) {
		User user = userRepository.findByEmail(model.getEmail()).orElseThrow(() -> new ApplicationException(USER_NOT_FOUND_BY_EMAIL, Collections.singletonMap(EMAIL, model.getEmail())));
		if (!user.getVerifyCode().equals(model.getVerificationCode())) {
			throw new ApplicationException(VERIFICATION_CODE_NOT_MATCH);
		}
		user.setPassword(passwordEncoder.encode(model.getPassword()));
		userRepository.saveAndFlush(user);
	}

	@Override
	public void deleteUser(@NonNull Long id) {
		User user = userRepository.findById(id).orElseThrow(() -> new ApplicationException(USER_NOT_FOUND, Collections.singletonMap(ID, id)));
		for (Location location : user.getFavoriteLocations()) {
			location.removeFollower(user);
		}
		userRepository.delete(user);
	}
}
