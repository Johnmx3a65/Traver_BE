package com.parovsky.traver.dao;

import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
	Optional<T> get(@NonNull Long id);

	List<T> getAll();

	T save(@NonNull T t);

	void delete(@NonNull T t);
}
