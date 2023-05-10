package com.parovsky.traver.dao;

import com.parovsky.traver.dto.model.CategoryModel;
import com.parovsky.traver.entity.Category;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

public interface CategoryDAO {
    List<Category> getAllCategories();

    List<Category> getFavoriteCategories(String email);

    @Nullable
    Category getCategoryById(@NonNull Long id);

    boolean isCategoryExistById(@NonNull Long id);

    boolean isCategoryExistByName(@NonNull String name);

    Category updateCategory(@NonNull CategoryModel categoryModel);

    Category saveCategory(@NonNull CategoryModel categoryModel);

    void deleteCategory(@NonNull Long id);
}
