package com.parovsky.traver.dao;

import com.parovsky.traver.dto.UserDTO;
import com.parovsky.traver.entity.User;
import org.springframework.lang.NonNull;

import java.util.List;

public interface UserDAO {

	List<User> getAllUsers();

	User getUserById(Long id);

	User getUserByEmail(String email);

	String getCurrentUserEmail();

	boolean isUserExist(Long id);

	boolean isUserExistByEmail(String email);

	User saveUser(@NonNull UserDTO userDTO);

	User updateUser(@NonNull UserDTO userDTO);

	void deleteUser(Long id);
}
