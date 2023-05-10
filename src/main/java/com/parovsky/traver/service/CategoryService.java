package com.parovsky.traver.service;

import com.parovsky.traver.dto.model.CategoryModel;
import com.parovsky.traver.entity.Category;
import com.parovsky.traver.exception.impl.CategoryIsAlreadyExistException;
import com.parovsky.traver.exception.impl.CategoryNotFoundException;
import org.springframework.lang.NonNull;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();

    List<Category> getFavoriteCategories();

    Category getCategoryById(@NonNull Long id) throws CategoryNotFoundException;

    Category saveCategory(@NonNull CategoryModel categoryModel) throws CategoryIsAlreadyExistException;

    Category updateCategory(@NonNull CategoryModel categoryModel) throws CategoryNotFoundException;

    void deleteCategory(@NonNull Long id) throws CategoryNotFoundException;
}
