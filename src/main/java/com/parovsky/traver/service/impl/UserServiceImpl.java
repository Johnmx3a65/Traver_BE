package com.parovsky.traver.service.impl;

import com.parovsky.traver.config.UserPrincipal;
import com.parovsky.traver.dao.UserDao;
import com.parovsky.traver.dto.model.*;
import com.parovsky.traver.dto.view.UserView;
import com.parovsky.traver.entity.User;
import com.parovsky.traver.exception.EntityAlreadyExistsException;
import com.parovsky.traver.exception.EntityNotFoundException;
import com.parovsky.traver.exception.VerificationCodeNotMatchException;
import com.parovsky.traver.role.Role;
import com.parovsky.traver.security.jwt.JwtUtils;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.parovsky.traver.utils.Utils.generateRandomString;
import static com.parovsky.traver.utils.Utils.generateVerificationCode;

@Service
@AllArgsConstructor(onConstructor = @__({@org.springframework.beans.factory.annotation.Autowired}))
public class UserServiceImpl implements UserService {

	private final UserDao userDAO;

	private final EmailServiceImpl emailService;

	private final ModelMapper modelMapper;

	private final AuthenticationManager authenticationManager;

	private final JwtUtils jwtUtils;

	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	public List<UserView> getAllUsers() {
		List<User> users = userDAO.getAll();
		return users.stream().map(user -> modelMapper.map(user, UserView.class)).collect(Collectors.toList());
	}

	@Override
	public UserView getUserById(@NonNull Long id) throws EntityNotFoundException {
		User user = userDAO.get(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
		return modelMapper.map(user, UserView.class);
	}

	@Override
	public UserView getCurrentUser() throws EntityNotFoundException {
		String userEmail = getCurrentUserEmail();
		User user = userDAO.getByEmail(userEmail).orElseThrow(() -> new EntityNotFoundException("User not found"));
		return modelMapper.map(user, UserView.class);
	}

	@Override
	public ResponseEntity<UserView> authenticateUser(@Valid @NonNull SignInModel model) {
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
				.role(roles.get(0).replace("ROLE_", ""))
				.build();

		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(userView);
	}

	@Override
	public ResponseEntity<Void> logoutUser() {
		ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
	}

	@Override
	public void sendVerificationEmail(@Valid @NonNull SendVerificationCodeModel model) throws EntityNotFoundException {
		User user = userDAO.getByEmail(model.getEmail()).orElseThrow(() -> new EntityNotFoundException("User not found"));
		int code = generateVerificationCode();
		emailService.sendEmail(user.getEmail(), "Verification code", "Your verification code is: " + code);
		user.setVerifyCode(String.valueOf(code));
		userDAO.save(user);
	}

	@Override
	public void checkVerificationCode(@NonNull CheckVerificationCodeModel model) throws EntityNotFoundException, VerificationCodeNotMatchException {
		User user = userDAO.getByEmail(model.getEmail()).orElseThrow(() -> new EntityNotFoundException("User not found"));
		if (!user.getVerifyCode().equals(model.getVerificationCode())) {
			throw new VerificationCodeNotMatchException();
		}
	}

	@Override
	public UserView saveUser(@Valid @NonNull SignUpModel model) throws EntityAlreadyExistsException {
		if (userDAO.isExistByEmail(model.getEmail())) {
			throw new EntityAlreadyExistsException("User already exist");
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
		User newUser = userDAO.save(user);
		return modelMapper.map(newUser, UserView.class);
	}

	@Override
	public UserView saveUserByAdmin(@NonNull @Valid SaveUserModel model) throws EntityAlreadyExistsException {
		if (userDAO.isExistByEmail(model.getEmail())) {
			throw new EntityAlreadyExistsException("User already exist");
		}
		String password = generateRandomString(10);
		emailService.sendEmail(model.getEmail(), "TRAVER CREDENTIALS", "Your password is " + password);

		User user = User.builder()
				.name(model.getName())
				.email(model.getEmail())
				.role(model.getRole().name())
				.password(passwordEncoder.encode(password))
				.build();
		User newUser = userDAO.save(user);
		return modelMapper.map(newUser, UserView.class);
	}

	@Override
	public UserView updateUser(@NonNull @Valid UpdateUserModel model) throws EntityNotFoundException {
		User user = userDAO.get(model.getId()).orElseThrow(() -> new EntityNotFoundException("User not found"));

		user.setName(model.getName());
		user.setEmail(model.getEmail());
		user.setRole(model.getRole().name());

		User result = userDAO.save(user);
		return modelMapper.map(result, UserView.class);
	}

	@Override
	public void resetPassword(@Valid @NonNull ResetPasswordModel model) throws EntityNotFoundException, VerificationCodeNotMatchException {
		User user = userDAO.getByEmail(model.getEmail()).orElseThrow(() -> new EntityNotFoundException("User not found"));
		if (!user.getVerifyCode().equals(model.getVerificationCode())) {
			throw new VerificationCodeNotMatchException();
		}
		user.setPassword(passwordEncoder.encode(model.getPassword()));
		userDAO.save(user);
	}

	@Override
	public void deleteUser(@NonNull Long id) throws EntityNotFoundException {
		User user = userDAO.get(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
		userDAO.delete(user);
	}

	private String getCurrentUserEmail() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String userEmail;

		if (principal instanceof UserDetails) {
			userEmail = ((UserDetails) principal).getUsername();
		} else {
			userEmail = principal.toString();
		}

		return userEmail;
	}
}
