package com.parovsky.traver.dao;

import com.parovsky.traver.dto.CategoryDTO;
import com.parovsky.traver.entity.Category;
import com.parovsky.traver.exception.impl.CategoryIsAlreadyExistException;
import com.parovsky.traver.exception.impl.CategoryNotFoundException;
import org.springframework.lang.NonNull;

import java.util.List;

public interface CategoryDAO {
    List<Category> getAllCategories();

    Category getCategoryById(Long id) throws CategoryNotFoundException;

    Category updateCategory(@NonNull CategoryDTO categoryDTO) throws CategoryNotFoundException;

    Category saveCategory(@NonNull CategoryDTO categoryDTO) throws CategoryIsAlreadyExistException;

    void deleteCategory(Long id) throws CategoryNotFoundException;
}
