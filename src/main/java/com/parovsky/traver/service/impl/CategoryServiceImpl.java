package com.parovsky.traver.service.impl;

import com.parovsky.traver.dao.CategoryDAO;
import com.parovsky.traver.dao.UserDAO;
import com.parovsky.traver.dto.CategoryDTO;
import com.parovsky.traver.entity.Category;
import com.parovsky.traver.exception.impl.CategoryIsAlreadyExistException;
import com.parovsky.traver.exception.impl.CategoryNotFoundException;
import com.parovsky.traver.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__({@org.springframework.beans.factory.annotation.Autowired}))
public class CategoryServiceImpl implements CategoryService {

	private final CategoryDAO categoryDAO;

	private final UserDAO userDAO;

	@Override
	public List<CategoryDTO> getAllCategories() {
		List<Category> categories = categoryDAO.getAllCategories();
		return categories.stream().map(CategoryService::transformCategoryToCategoryDTO).collect(Collectors.toList());
	}

	@Override
	public List<CategoryDTO> getFavoriteCategories() {
		String userEmail = userDAO.getCurrentUserEmail();
		List<Category> categories = categoryDAO.getFavoriteCategories(userEmail);
		return categories.stream().map(CategoryService::transformCategoryToCategoryDTO).collect(Collectors.toList());
	}

	@Override
	public CategoryDTO getCategoryById(@NonNull Long id) throws CategoryNotFoundException {
		Category category = categoryDAO.getCategoryById(id);
		if (category == null) {
			throw new CategoryNotFoundException();
		}
		return CategoryService.transformCategoryToCategoryDTO(category);
	}

	@Override
	public CategoryDTO saveCategory(@NonNull CategoryDTO categoryDTO) throws CategoryIsAlreadyExistException {
		if (categoryDAO.isCategoryExistByName(categoryDTO.getName())) {
			throw new CategoryIsAlreadyExistException();
		}
		return CategoryService.transformCategoryToCategoryDTO(categoryDAO.saveCategory(categoryDTO));
	}

	@Override
	public CategoryDTO updateCategory(@NonNull CategoryDTO categoryDTO) throws CategoryNotFoundException {
		if (!categoryDAO.isCategoryExistById(categoryDTO.getId())) {
			throw new CategoryNotFoundException();
		}
		return CategoryService.transformCategoryToCategoryDTO(categoryDAO.updateCategory(categoryDTO));
	}

	@Override
	public void deleteCategory(@NonNull Long id) throws CategoryNotFoundException {
		if (!categoryDAO.isCategoryExistById(id)) {
			throw new CategoryNotFoundException();
		}
		categoryDAO.deleteCategory(id);
	}
}
