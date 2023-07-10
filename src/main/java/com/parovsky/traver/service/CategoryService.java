package com.parovsky.traver.service;

import com.parovsky.traver.dto.model.SaveCategoryModel;
import com.parovsky.traver.dto.model.UpdateCategoryModel;
import com.parovsky.traver.entity.Category;
import com.parovsky.traver.exception.EntityAlreadyExistsException;
import com.parovsky.traver.exception.EntityNotFoundException;
import org.springframework.lang.NonNull;

import javax.validation.Valid;
import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();

    List<Category> getFavoriteCategories() throws EntityNotFoundException;

    Category getCategoryById(@NonNull Long id) throws EntityNotFoundException;

    Category saveCategory(@Valid @NonNull SaveCategoryModel model) throws EntityAlreadyExistsException;

    Category updateCategory(@Valid @NonNull UpdateCategoryModel model) throws EntityNotFoundException;

    void deleteCategory(@NonNull Long id) throws EntityNotFoundException;
}
