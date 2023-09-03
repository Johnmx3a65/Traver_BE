package com.parovsky.traver.service;

import com.parovsky.traver.dto.model.SaveCategoryModel;
import com.parovsky.traver.dto.model.UpdateCategoryModel;
import com.parovsky.traver.entity.Category;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.Valid;
import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();

    List<Category> getFavoriteCategories(UserDetails userDetails);

    Category getCategoryById(@NonNull Long id);

    Category saveCategory(@Valid @NonNull SaveCategoryModel model);

    Category updateCategory(@Valid @NonNull UpdateCategoryModel model);

    void deleteCategory(@NonNull Long id);
}
