package com.parovsky.traver.dao;

import com.parovsky.traver.entity.User;

import java.util.Optional;

public interface UserDao extends Dao<User> {
	Optional<User> getByEmail(String email);

	boolean isExistByEmail(String email);
}