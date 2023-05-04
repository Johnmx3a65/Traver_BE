package com.parovsky.traver.dao.impl;

import com.parovsky.traver.dao.CategoryDAO;
import com.parovsky.traver.dto.CategoryDTO;
import com.parovsky.traver.entity.Category;
import com.parovsky.traver.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
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
	public List<Category> getFavoriteCategories(Long userId) {
		return categoryRepository.findFovoriteCategories(userId);
	}

	@Override
	public @Nullable Category getCategoryById(Long id) {
		return categoryRepository.findById(id).orElse(null);
	}

	@Override
	public boolean isCategoryExistById(Long id) {
		return categoryRepository.existsById(id);
	}

	@Override
	public boolean isCategoryExistByName(String name) {
		return categoryRepository.existsByName(name);
	}

	@Override
	public Category updateCategory(@NonNull CategoryDTO categoryDTO) {
		Category category = categoryRepository.getById(categoryDTO.getId());
		category.setName(categoryDTO.getName());
		category.setPicture(categoryDTO.getPicture());
		categoryRepository.saveAndFlush(category);
		return category;
	}

	@Override
	public Category saveCategory(@NonNull CategoryDTO categoryDTO) {
		Category category = new Category();
		category.setName(categoryDTO.getName());
		category.setPicture(categoryDTO.getPicture());
		categoryRepository.saveAndFlush(category);
		return category;
	}

	@Override
	public void deleteCategory(Long id) {
		categoryRepository.deleteById(id);
	}
}


