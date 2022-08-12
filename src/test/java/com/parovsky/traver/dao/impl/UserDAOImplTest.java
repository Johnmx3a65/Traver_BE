package com.parovsky.traver.dao.impl;

import com.parovsky.traver.dto.UserDTO;
import com.parovsky.traver.entity.User;
import com.parovsky.traver.repository.UserRepository;
import com.parovsky.traver.role.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class UserDAOImplTest {

	private final Long user1Id = 1L;

	private final Long user2Id = 2L;

	private final String user1Email = "email1";

	private final String user2Email = "email2";

	private final String user1NameUpdated = "name1Updated";

	private final User user1 = new User(
			user1Id,
			"name1",
			user1Email,
			"password1",
			Role.USER.name()
	);

	private final User user2 = new User(
			user2Id,
			"name2",
			user2Email,
			"password2",
			Role.ADMIN.name()
	);

	private final List<User> users = Arrays.asList(user1, user2);

	private final UserDTO userDTO1 = new UserDTO(
			user1Id,
			"name1",
			user1Email,
			"phone1",
			"role1",
			"verificationCode1"
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
	void getUserById() {
		doReturn(user1).when(userRepository).getById(user1Id);
		User result = subject.getUserById(user1Id);
		assertEquals(user1, result);
	}

	@Test
	void getUserByEmail() {
		doReturn(user1).when(userRepository).getByEmail(user1Email);
		User result = subject.getUserByEmail(user1Email);
		assertEquals(user1, result);
	}

	@Test
	void isUserExistByIdShouldReturnTrue() {
		doReturn(true).when(userRepository).existsById(user1Id);
		boolean result = subject.isUserExist(user1Id);
		assertTrue(result);
	}

	@Test
	void isUserExistByIdShouldReturnFalse() {
		doReturn(false).when(userRepository).existsById(user1Id);
		boolean result = subject.isUserExist(user1Id);
		assertFalse(result);
	}

	@Test
	void isUserExistByEmailShouldReturnTrue() {
		doReturn(true).when(userRepository).existsUserByEmail(user1Email);
		boolean result = subject.isUserExistByEmail(user1Email);
		assertTrue(result);
	}

	@Test
	void saveUser() {
		doReturn(false).when(userRepository).existsUserByEmail(user1Email);
		doReturn(user1).when(userRepository).saveAndFlush(any(User.class));
		User result = subject.saveUser(userDTO1);
		assertEquals(user1.getId(), result.getId());
		assertEquals(user1.getEmail(), result.getEmail());
		assertEquals(user1.getPassword(), result.getPassword());
		assertEquals(user1.getRole(), result.getRole());
	}

	@Test
	void updateUser() {
		doReturn(user1).when(userRepository).getById(user1Id);
		doReturn(user1Updated).when(userRepository).saveAndFlush(any(User.class));
		User result = subject.updateUser(userDTO1);
		assertEquals(user1.getId(), result.getId());
		assertEquals(user1NameUpdated, result.getName());
	}

	@Test
	void deleteUser() {
		assertDoesNotThrow(() -> subject.deleteUser(user1Id));
	}

}