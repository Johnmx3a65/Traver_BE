package com.parovsky.traver.service.impl;

import com.parovsky.traver.config.UserPrincipal;
import com.parovsky.traver.dao.UserDAO;
import com.parovsky.traver.dto.model.UserModel;
import com.parovsky.traver.dto.view.UserView;
import com.parovsky.traver.entity.User;
import com.parovsky.traver.exception.impl.UserIsAlreadyExistException;
import com.parovsky.traver.exception.impl.UserNotFoundException;
import com.parovsky.traver.exception.impl.VerificationCodeNotMatchException;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.parovsky.traver.utils.Utils.generateRandomString;
import static com.parovsky.traver.utils.Utils.generateVerificationCode;

@Service
@AllArgsConstructor(onConstructor = @__({@org.springframework.beans.factory.annotation.Autowired}))
public class UserServiceImpl implements UserService {

	private final UserDAO userDAO;

	private final EmailServiceImpl emailService;

	private final ModelMapper modelMapper;

	private final AuthenticationManager authenticationManager;

	private final JwtUtils jwtUtils;

	@Override
	public List<UserView> getAllUsers() {
		List<User> users = userDAO.getAllUsers();
		return users
				.stream()
				.map(user -> modelMapper.map(user, UserView.class))
				.collect(Collectors.toList());
	}

	@Override
	public UserView getUserById(@NonNull Long id) throws UserNotFoundException {
		User user = userDAO.getUserById(id);
		if (user == null) {
			throw new UserNotFoundException();
		}
		return modelMapper.map(user, UserView.class);
	}

	@Override
	public UserView getUserByEmail(@NonNull String email) throws UserNotFoundException {
		User user = userDAO.getUserByEmail(email);
		if (user == null) {
			throw new UserNotFoundException();
		}
		return modelMapper.map(user, UserView.class);
	}

	@Override
	public UserView getCurrentUser() {
		User user = userDAO.getCurrentUser();
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
	public void sendVerificationEmail(@NonNull UserModel userModel) throws UserNotFoundException {
		User user = userDAO.getUserByEmail(userModel.getEmail());
		if (user == null) {
			throw new UserNotFoundException();
		}
		int code = generateVerificationCode();
		emailService.sendEmail(userModel.getEmail(), "Verification code", "Your verification code is: " + code);
		userModel.setVerifyCode(String.valueOf(code));
		userDAO.updateVerificationCode(userModel);
	}

	@Override
	public void checkVerificationCode(@NonNull UserModel userModel) throws UserNotFoundException, VerificationCodeNotMatchException {
		User user = userDAO.getUserByEmail(userModel.getEmail());
		if (user == null) {
			throw new UserNotFoundException();
		}
		if (isCodeNotMatch(userModel, user)) {
			throw new VerificationCodeNotMatchException();
		}
	}

	@Override
	public UserView saveUser(@NonNull UserModel userModel) throws UserIsAlreadyExistException {
		if (userDAO.isUserExistByEmail(userModel.getEmail())) {
			throw new UserIsAlreadyExistException();
		}

		int code = generateVerificationCode();
		emailService.sendEmail(userModel.getEmail(), "Verification code", "Your verification code is: " + code);
		userModel.setVerifyCode(String.valueOf(code));

		User user = userDAO.saveUser(userModel);
		return modelMapper.map(user, UserView.class);
	}

	@Override
	public UserView saveUserByAdmin(@NonNull UserModel userModel) throws UserIsAlreadyExistException {
		if (userDAO.isUserExistByEmail(userModel.getEmail())) {
			throw new UserIsAlreadyExistException();
		}
		userModel.setPassword(generateRandomString(10));
		emailService.sendEmail(userModel.getEmail(), "TRAVER PASSWORD UPDATE", "Your new password is " + userModel.getPassword());

		User user = userDAO.saveUser(userModel);
		return modelMapper.map(user, UserView.class);
	}

	@Override
	public UserView updateUser(@NonNull UserModel userModel) throws UserNotFoundException {
		if (!userDAO.isUserExist(userModel.getId())) {
			throw new UserNotFoundException();
		}
		User user = userDAO.updateUser(userModel);
		return modelMapper.map(user, UserView.class);
	}

	@Override
	public void resetPassword(@NonNull UserModel userModel) throws UserNotFoundException, VerificationCodeNotMatchException {
		User user = userDAO.getUserByEmail(userModel.getEmail());
		if (user == null) {
			throw new UserNotFoundException();
		}
		if (isCodeNotMatch(userModel, user)) {
			throw new VerificationCodeNotMatchException();
		}
		userModel.setPassword(generateRandomString(10));
		emailService.sendEmail(userModel.getEmail(), "TRAVER PASSWORD UPDATE", "Your new password is " + userModel.getPassword());
		userDAO.updatePassword(userModel);
	}

	@Override
	public void deleteUser(@NonNull Long id) throws UserNotFoundException {
		if (!userDAO.isUserExist(id)) {
			throw new UserNotFoundException();
		}
		userDAO.deleteUser(id);
	}

	private boolean isCodeNotMatch(@NonNull UserModel userModel, @NonNull User user) {
		return !user.getVerifyCode().equals(userModel.getVerifyCode());
	}
}
