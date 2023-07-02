package com.parovsky.traver.service.impl;

import com.parovsky.traver.config.UserPrincipal;
import com.parovsky.traver.dao.UserDao;
import com.parovsky.traver.dto.model.ResetPasswordModel;
import com.parovsky.traver.dto.model.UserModel;
import com.parovsky.traver.dto.view.UserView;
import com.parovsky.traver.entity.User;
import com.parovsky.traver.exception.impl.UserIsAlreadyExistException;
import com.parovsky.traver.exception.impl.UserNotFoundException;
import com.parovsky.traver.exception.impl.VerificationCodeNotMatchException;
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
	public UserView getUserById(@NonNull Long id) throws UserNotFoundException {
		User user = userDAO.get(id).orElseThrow(UserNotFoundException::new);
		return modelMapper.map(user, UserView.class);
	}

	@Override
	public UserView getCurrentUser() throws UserNotFoundException {
		String userEmail = getCurrentUserEmail();
		User user = userDAO.getByEmail(userEmail).orElseThrow(UserNotFoundException::new);
		return modelMapper.map(user, UserView.class);
	}

	@Override
	public ResponseEntity<UserView> authenticateUser(@NonNull UserModel userModel) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						userModel.getEmail(),
						userModel.getPassword()
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
	public void sendVerificationEmail(@NonNull UserModel userModel) throws UserNotFoundException {
		User user = userDAO.getByEmail(userModel.getEmail()).orElseThrow(UserNotFoundException::new);
		int code = generateVerificationCode();
		emailService.sendEmail(user.getEmail(), "Verification code", "Your verification code is: " + code);
		user.setVerifyCode(String.valueOf(code));
		userDAO.save(user);
	}

	@Override
	public void checkVerificationCode(@NonNull UserModel userModel) throws UserNotFoundException, VerificationCodeNotMatchException {
		User user = userDAO.getByEmail(userModel.getEmail()).orElseThrow(UserNotFoundException::new);
		if (!user.getVerifyCode().equals(userModel.getVerifyCode())) {
			throw new VerificationCodeNotMatchException();
		}
	}

	@Override
	public UserView saveUser(@NonNull UserModel userModel) throws UserIsAlreadyExistException {
		if (userDAO.isExistByEmail(userModel.getEmail())) {
			throw new UserIsAlreadyExistException();
		}

		int code = generateVerificationCode();
		emailService.sendEmail(userModel.getEmail(), "TRAVER VERIFICATION CODE", "Your verification code is: " + code);

		User user = User.builder()
				.name(userModel.getName())
				.email(userModel.getEmail())
				.role(userModel.getRole().equals(Role.ADMIN.name()) ? Role.ADMIN.name() : Role.USER.name())
				.password(passwordEncoder.encode(userModel.getPassword()))
				.verifyCode(String.valueOf(code))
				.build();
		User newUser = userDAO.save(user);
		return modelMapper.map(newUser, UserView.class);
	}

	@Override
	public UserView saveUserByAdmin(@NonNull UserModel userModel) throws UserIsAlreadyExistException {
		if (userDAO.isExistByEmail(userModel.getEmail())) {
			throw new UserIsAlreadyExistException();
		}
		userModel.setPassword(generateRandomString(10));
		emailService.sendEmail(userModel.getEmail(), "TRAVER PASSWORD UPDATE", "Your new password is " + userModel.getPassword());

		User user = User.builder()
				.name(userModel.getName())
				.email(userModel.getEmail())
				.role(userModel.getRole().equals(Role.ADMIN.name()) ? Role.ADMIN.name() : Role.USER.name())
				.password(passwordEncoder.encode(userModel.getPassword()))
				.build();
		User newUser = userDAO.save(user);
		return modelMapper.map(newUser, UserView.class);
	}

	@Override
	public UserView updateUser(@NonNull UserModel userModel) throws UserNotFoundException {
		User user = userDAO.get(userModel.getId()).orElseThrow(UserNotFoundException::new);

		user.setName(userModel.getName());
		user.setEmail(userModel.getEmail());
		user.setRole(userModel.getRole());

		User result = userDAO.save(user);
		return modelMapper.map(result, UserView.class);
	}

	@Override
	public void resetPassword(@NonNull ResetPasswordModel resetPasswordModel) throws UserNotFoundException, VerificationCodeNotMatchException {
		User user = userDAO.getByEmail(resetPasswordModel.getEmail()).orElseThrow(UserNotFoundException::new);
		if (!user.getVerifyCode().equals(resetPasswordModel.getVerifyCode())) {
			throw new VerificationCodeNotMatchException();
		}

		user.setPassword(passwordEncoder.encode(resetPasswordModel.getPassword()));
		userDAO.save(user);
	}

	@Override
	public void deleteUser(@NonNull Long id) throws UserNotFoundException {
		User user = userDAO.get(id).orElseThrow(UserNotFoundException::new);
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
