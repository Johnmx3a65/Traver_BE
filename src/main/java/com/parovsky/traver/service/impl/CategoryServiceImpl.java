package com.parovsky.traver.service.impl;

import com.parovsky.traver.dao.CategoryDAO;
import com.parovsky.traver.dto.CategoryDTO;
import com.parovsky.traver.entity.Category;
import com.parovsky.traver.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryDAO categoryDAO;

	@Autowired
	public CategoryServiceImpl(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}

	@Override
	public List<CategoryDTO> getAllCategories() {
		List<Category> categories = categoryDAO.getAllCategories();
		return categories.stream().map(CategoryService::transformCategoryToCategoryDTO).collect(Collectors.toList());
	}

	@Override
	public CategoryDTO getCategoryById(Long id) {
		Category category = categoryDAO.getCategoryById(id);
		return CategoryService.transformCategoryToCategoryDTO(category);
	}

	@Override
	public boolean isCategoryExistById(Long id) {
		return categoryDAO.isCategoryExistById(id);
	}

	@Override
	public boolean isCategoryExistByName(String name) {
		return categoryDAO.isCategoryExistByName(name);
	}

	@Override
	public CategoryDTO saveCategory(@NonNull CategoryDTO categoryDTO) {
		return CategoryService.transformCategoryToCategoryDTO(categoryDAO.saveCategory(categoryDTO));
	}

	@Override
	public CategoryDTO updateCategory(@NonNull CategoryDTO categoryDTO) {
		return CategoryService.transformCategoryToCategoryDTO(categoryDAO.updateCategory(categoryDTO));
	}

	@Override
	public void deleteCategory(Long id) {
		categoryDAO.deleteCategory(id);
	}
}
