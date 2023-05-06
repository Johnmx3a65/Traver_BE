package com.parovsky.traver.dao;

import com.parovsky.traver.dto.UserModel;
import com.parovsky.traver.entity.User;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

public interface UserDAO {

	List<User> getAllUsers();

	@Nullable
	User getUserById(Long id);

	@Nullable
	User getUserByEmail(String email);

	@Nullable
	User getCurrentUser();

	String getCurrentUserEmail();

	boolean isUserExist(Long id);

	boolean isUserExistByEmail(String email);

	User saveUser(@NonNull UserModel userModel);

	User updateUser(@NonNull UserModel userModel);

	void deleteUser(Long id);
}
