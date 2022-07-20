package com.parovsky.traver.dao.impl;

import com.parovsky.traver.dto.UserDTO;
import com.parovsky.traver.entity.User;
import com.parovsky.traver.exception.impl.UserIsAlreadyExistException;
import com.parovsky.traver.exception.impl.UserNotFoundException;
import com.parovsky.traver.repository.UserRepository;
import com.parovsky.traver.role.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserDAOImplTest {

	private final Long user1Id = 1L;

	private final Long user2Id = 2L;

	private final String user1Email = "email1";

	private final String user2Email = "email2";

	private final String user1NameUpdated = "name1Updated";

	private final Optional<User> user1 = Optional.of(new User(
			user1Id,
			"name1",
			user1Email,
			"password1",
			Role.USER.name()
	));

	private final Optional<User> user2 = Optional.of(new User(
			user2Id,
			"name2",
			user2Email,
			"password2",
			Role.ADMIN.name()
	));

	private final List<User> users = Arrays.asList(user1.get(), user2.get());

	private final UserDTO userDTO1 = new UserDTO(
			user1Id,
			"name1",
			user1Email,
			"phone1",
			"role1",
			"verificationCode1",
			new LinkedList<>()
	);

	private final User user1Updated = new User(
			user1Id,
			user1NameUpdated,
			user1Email,
			"password1",
			Role.USER.name()
	);

	private UserDAOImpl subject;

	private UserRepository userRepository;

	@BeforeEach
	void setUp() {
		userRepository = mock(UserRepository.class);
		subject = new UserDAOImpl(userRepository);
	}

	@Test
	void getAllUsers() {
		doReturn(users).when(userRepository).findAll();
		List<User> result = subject.getAllUsers();
		assertEquals(users, result);
	}

	@Test
	void getUserById() throws UserNotFoundException {
		doReturn(user1).when(userRepository).findById(user1Id);
		User result = subject.getUserById(user1Id);
		assertEquals(user1.get(), result);
	}

	@Test
	void getUserByEmail() throws UserNotFoundException {
		doReturn(user1).when(userRepository).findByEmail(user1Email);
		User result = subject.getUserByEmail(user1Email);
		assertEquals(user1.get(), result);
	}

	@Test
	void isUserExistShouldReturnTrue() {
		doReturn(true).when(userRepository).existsById(user1Id);
		boolean result = subject.isUserExist(user1Id);
		assertTrue(result);
	}

	@Test
	void isUserExistShouldReturnFalse() {
		doReturn(false).when(userRepository).existsById(user1Id);
		boolean result = subject.isUserExist(user1Id);
		assertFalse(result);
	}

	@Test
	void saveUser() throws UserIsAlreadyExistException {
		User user = user1.get();
		doReturn(false).when(userRepository).existsUserByEmail(user1Email);
		doReturn(user).when(userRepository).saveAndFlush(any(User.class));
		User result = subject.saveUser(userDTO1);
		assertEquals(user.getId(), result.getId());
		assertEquals(user.getEmail(), result.getEmail());
		assertEquals(user.getPassword(), result.getPassword());
		assertEquals(user.getRole(), result.getRole());
	}

	@Test
	void saveUserShouldThrowException() {
		doReturn(true).when(userRepository).existsUserByEmail(user1Email);
		assertThrows(UserIsAlreadyExistException.class, () -> subject.saveUser(userDTO1));
	}

	@Test
	void updateUser() throws UserNotFoundException {
		User user = user1.get();
		doReturn(user1).when(userRepository).findById(user1Id);
		doReturn(user1Updated).when(userRepository).saveAndFlush(any(User.class));
		User result = subject.updateUser(userDTO1);
		assertEquals(user.getId(), result.getId());
		assertEquals(user1NameUpdated, result.getName());
	}

	@Test
	void deleteUser() {
		int DELETED_USER_COUNT = 1;
		doReturn(DELETED_USER_COUNT).when(userRepository).deleteAllById(user1Id);
		assertDoesNotThrow(() -> subject.deleteUser(user1Id));
	}

	@Test
	void deleteUserShouldThrowException() {
		int DELETED_USER_COUNT = 0;
		doReturn(DELETED_USER_COUNT).when(userRepository).deleteAllById(user1Id);
		assertThrows(UserNotFoundException.class, () -> subject.deleteUser(user1Id));
	}
}