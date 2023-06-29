package com.parovsky.traver.dao.impl;

import com.parovsky.traver.dao.CategoryDAO;
import com.parovsky.traver.dto.model.CategoryModel;
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
	@NonNull
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public List<Category> getFavoriteCategories(@NonNull String email) {
		return categoryRepository.findFavouriteCategories(email);
	}

	@Override
	@Nullable
	public Category getCategoryById(@NonNull Long id) {
		return categoryRepository.findById(id).orElse(null);
	}

	@Override
	public boolean isCategoryExistById(@NonNull Long id) {
		return categoryRepository.existsById(id);
	}

	@Override
	public boolean isCategoryExistByName(@NonNull String name) {
		return categoryRepository.existsByName(name);
	}

	@Override
	public Category updateCategory(@NonNull CategoryModel categoryModel) {
		Category category = categoryRepository.getById(categoryModel.getId());
		category.setName(categoryModel.getName());
		category.setPicture(categoryModel.getPicture());
		return categoryRepository.saveAndFlush(category);
	}

	@Override
	public Category saveCategory(@NonNull CategoryModel categoryModel) {
		Category category = new Category();
		category.setName(categoryModel.getName());
		category.setPicture(categoryModel.getPicture());
		return categoryRepository.saveAndFlush(category);
	}

	@Override
	public void deleteCategory(@NonNull Long id) {
		categoryRepository.deleteById(id);
	}
}


