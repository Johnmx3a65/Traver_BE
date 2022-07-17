package com.parovsky.traver.dao.impl;

import com.parovsky.traver.dto.UserDTO;
import com.parovsky.traver.entity.User;
import com.parovsky.traver.exception.impl.UserNotFoundException;
import com.parovsky.traver.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class UserDAOImplTest {

    private UserDAOImpl subject;

    private UserRepository userRepository;

    private final Long user1Id = 1L;

    private final Long user2Id = 2L;

    private final String user1Email = "email1";

    private final String user2Email = "email2";

    private final Optional<User> user1 = Optional.of(new User(
            user1Id,
            "name1",
            user1Email,
            "password1",
            "role1"
    ));

    private final Optional<User> user2 = Optional.of(new User(
            user2Id,
            "name2",
            user2Email,
            "password2",
            "role2"
    ));

    private final UserDTO userDTO1 = new UserDTO(
            null,
            "name1",
            user1Email,
            "phone1",
            "role1",
            null,
            null
    );

    private final List<User> users = Arrays.asList(user1.get(), user2.get());

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
    void isUserExist() {
    }

    @Test
    void saveUser() {
        doReturn(false).when(userRepository).existsUserByEmail(user1Email);
        doReturn(user1).when(userRepository).saveAndFlush(user1.get());
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }
}