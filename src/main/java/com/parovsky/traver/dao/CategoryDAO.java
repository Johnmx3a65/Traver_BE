package com.parovsky.traver.dao;

import com.parovsky.traver.dto.CategoryDTO;
import com.parovsky.traver.entity.Category;
import org.springframework.lang.NonNull;

import java.util.List;

public interface CategoryDAO {
    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    boolean isCategoryExistById(Long id);

    boolean isCategoryExistByName(String name);

    Category updateCategory(@NonNull CategoryDTO categoryDTO);

    Category saveCategory(@NonNull CategoryDTO categoryDTO);

    void deleteCategory(Long id);
}
