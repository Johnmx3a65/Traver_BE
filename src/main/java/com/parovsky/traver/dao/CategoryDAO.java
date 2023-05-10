package com.parovsky.traver.dao;

import com.parovsky.traver.dto.model.SaveCategoryModel;
import com.parovsky.traver.dto.model.UpdateCategoryModel;
import com.parovsky.traver.entity.Category;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

public interface CategoryDAO {
    List<Category> getAllCategories();

    List<Category> getFavoriteCategories(String email);

    @Nullable
    Category getCategoryById(Long id);

    boolean isCategoryExistById(Long id);

    boolean isCategoryExistByName(String name);

    Category updateCategory(@NonNull UpdateCategoryModel updateCategoryModel);

    Category saveCategory(@NonNull SaveCategoryModel saveCategoryModel);

    void deleteCategory(Long id);
}
