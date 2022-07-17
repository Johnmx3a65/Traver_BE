package com.parovsky.traver.dao.impl;

import com.parovsky.traver.dao.CategoryDAO;
import com.parovsky.traver.dto.CategoryDTO;
import com.parovsky.traver.entity.Category;
import com.parovsky.traver.exception.impl.CategoryIsAlreadyExistException;
import com.parovsky.traver.exception.impl.CategoryNotFoundException;
import com.parovsky.traver.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class CategoryDAOImpl implements CategoryDAO {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryDAOImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public @NonNull
    List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) throws CategoryNotFoundException {
        return categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
    }

    @Override
    public Category updateCategory(@NonNull CategoryDTO categoryDTO) throws CategoryNotFoundException {
        Category category = categoryRepository.findById(categoryDTO.getId()).orElseThrow(CategoryNotFoundException::new);
        category.setName(categoryDTO.getName());
        category.setPicture(categoryDTO.getPicture());
        categoryRepository.saveAndFlush(category);
        return category;
    }

    @Override
    public Category saveCategory(@NonNull CategoryDTO categoryDTO) throws CategoryIsAlreadyExistException {
        if (categoryRepository.existsByName(categoryDTO.getName())) {
            throw new CategoryIsAlreadyExistException();
        } else {
            Category category = new Category();
            category.setName(categoryDTO.getName());
            category.setPicture(categoryDTO.getPicture());
            categoryRepository.saveAndFlush(category);
            return category;
        }
    }

    @Override
    public void deleteCategory(Long id) throws CategoryNotFoundException {
        if (categoryRepository.deleteAllById(id) == 0) {
            throw new CategoryNotFoundException();
        }
    }
}


