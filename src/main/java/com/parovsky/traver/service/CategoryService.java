package com.parovsky.traver.service;

import com.parovsky.traver.dto.model.CategoryModel;
import com.parovsky.traver.entity.Category;
import com.parovsky.traver.exception.EntityAlreadyExistsException;
import com.parovsky.traver.exception.EntityNotFoundException;
import org.springframework.lang.NonNull;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();

    List<Category> getFavoriteCategories() throws EntityNotFoundException;

    Category getCategoryById(@NonNull Long id) throws EntityNotFoundException;

    Category saveCategory(@NonNull CategoryModel categoryModel) throws EntityAlreadyExistsException;

    Category updateCategory(@NonNull CategoryModel categoryModel) throws EntityNotFoundException;

    void deleteCategory(@NonNull Long id) throws EntityNotFoundException;
}
