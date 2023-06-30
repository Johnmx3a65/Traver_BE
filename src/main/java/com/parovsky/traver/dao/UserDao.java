package com.parovsky.traver.dao;

import com.parovsky.traver.entity.User;
import org.springframework.lang.NonNull;

import java.util.Optional;

public interface UserDao extends Dao<User> {

	User update(@NonNull User user, String[] params);

	Optional<User> getByEmail(String email);

	boolean isExistByEmail(String email);
}