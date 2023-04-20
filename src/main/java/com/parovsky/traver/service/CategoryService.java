package com.parovsky.traver.service;

import com.parovsky.traver.dto.CategoryDTO;
import com.parovsky.traver.entity.Category;
import org.springframework.lang.NonNull;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getAllCategories();

    List<CategoryDTO> getFavoriteCategories(Long userId);

    CategoryDTO getCategoryById(Long id);

    boolean isCategoryExistById(Long id);

    boolean isCategoryExistByName(String name);

    CategoryDTO saveCategory(@NonNull CategoryDTO categoryDTO);

    CategoryDTO updateCategory(@NonNull CategoryDTO categoryDTO);

    void deleteCategory(Long id);

    static CategoryDTO transformCategoryToCategoryDTO(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName(),
                category.getPicture()
        );
    }
}
