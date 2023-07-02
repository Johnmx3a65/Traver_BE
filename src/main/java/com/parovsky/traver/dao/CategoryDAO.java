package com.parovsky.traver.dao;

import com.parovsky.traver.entity.Category;
import org.springframework.lang.NonNull;

import java.util.List;

public interface CategoryDAO extends Dao<Category> {

    List<Category> getAllFavorite(String email);

    boolean isExistById(@NonNull Long id);

    boolean isExistByName(@NonNull String name);
}
