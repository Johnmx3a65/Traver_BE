package com.parovsky.traver.service.impl;

import com.parovsky.traver.dao.UserDAO;
import com.parovsky.traver.dto.UserModel;
import com.parovsky.traver.dto.view.UserView;
import com.parovsky.traver.entity.User;
import com.parovsky.traver.exception.impl.UserIsAlreadyExistException;
import com.parovsky.traver.exception.impl.UserNotFoundException;
import com.parovsky.traver.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.parovsky.traver.utils.Utils.generateRandomString;

@Service
@AllArgsConstructor(onConstructor = @__({@org.springframework.beans.factory.annotation.Autowired}))
public class UserServiceImpl implements UserService {

	private final UserDAO userDAO;

	private final EmailServiceImpl emailService;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	private final ModelMapper modelMapper;

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
	public boolean isUserExistByEmail(String email) {
		return userDAO.isUserExistByEmail(email);
	}

	@Override
	public UserView saveUser(@NonNull UserModel userModel) throws UserIsAlreadyExistException {
		if (userDAO.isUserExistByEmail(userModel.getEmail())) {
			throw new UserIsAlreadyExistException();
		}
		if (userModel.getPassword() == null) {
			userModel.setPassword(generateRandomString(10));
			emailService.sendEmail(userModel.getEmail(), "TRAVER PASSWORD UPDATE", "Your new password is " + userModel.getPassword());
		}
		userModel.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));
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
	public void resetPassword(UserModel userModel) {
		User user = userDAO.getUserByEmail(userModel.getEmail());
		user.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));
		userDAO.updateUser(UserService.transformUserToUserDTO(user));
	}

	@Override
	public boolean checkVerificationCode(UserModel userModel) {
		User user = userDAO.getUserByEmail(userModel.getEmail());
		return user.getVerifyCode().equals(userModel.getVerifyCode());
	}

	@Override
	public void deleteUser(@NonNull Long id) throws UserNotFoundException {
		if (!userDAO.isUserExist(id)) {
			throw new UserNotFoundException();
		}
		userDAO.deleteUser(id);
	}
}
