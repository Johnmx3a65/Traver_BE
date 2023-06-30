package com.parovsky.traver.dao.impl;

import com.parovsky.traver.dao.UserDao;
import com.parovsky.traver.entity.User;
import com.parovsky.traver.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@Transactional
@AllArgsConstructor(onConstructor = @__({@org.springframework.beans.factory.annotation.Autowired}))
public class UserDaoImpl implements UserDao {

	private final UserRepository userRepository;

	@Override
	public List<User> getAll() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> get(@NonNull Long id) {
		return userRepository.findById(id);
	}

	@Override
	public Optional<User> getByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public boolean isExistByEmail(String email) {
		return userRepository.existsUserByEmail(email);
	}

	@Override
	public User save(@NonNull User user) {
		return userRepository.saveAndFlush(user);
	}

	@Override
	public User update(@NonNull User user, String[] params) {
		user.setName(Objects.requireNonNull(params[0], "Name cannot be null"));
		user.setEmail(Objects.requireNonNull(params[1], "Email cannot be null"));
		user.setRole(Objects.requireNonNull(params[2], "Role cannot be null"));
		user.setPassword(Objects.requireNonNull(params[3], "Password cannot be null"));
		user.setVerifyCode(params[4]);
		return userRepository.saveAndFlush(user);
	}

	@Override
	public void delete(@NonNull User user) {
		userRepository.deleteById(user.getId());
	}
}
