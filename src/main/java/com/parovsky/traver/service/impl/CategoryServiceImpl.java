package com.parovsky.traver.service.impl;

import com.parovsky.traver.dao.CategoryDAO;
import com.parovsky.traver.dao.UserDAO;
import com.parovsky.traver.dto.model.CategoryModel;
import com.parovsky.traver.entity.Category;
import com.parovsky.traver.exception.impl.CategoryIsAlreadyExistException;
import com.parovsky.traver.exception.impl.CategoryNotFoundException;
import com.parovsky.traver.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__({@org.springframework.beans.factory.annotation.Autowired}))
public class CategoryServiceImpl implements CategoryService {

	private final CategoryDAO categoryDAO;

	private final UserDAO userDAO;

	@Override
	public List<Category> getAllCategories() {
		return categoryDAO.getAllCategories();
	}

	@Override
	public List<Category> getFavoriteCategories() {
		String userEmail = userDAO.getCurrentUserEmail();
		return categoryDAO.getFavoriteCategories(userEmail);
	}

	@Override
	public Category getCategoryById(@NonNull Long id) throws CategoryNotFoundException {
		Category category = categoryDAO.getCategoryById(id);
		if (category == null) {
			throw new CategoryNotFoundException();
		}
		return category;
	}

	@Override
	public Category saveCategory(@NonNull CategoryModel categoryModel) throws CategoryIsAlreadyExistException {
		if (categoryDAO.isCategoryExistByName(categoryModel.getName())) {
			throw new CategoryIsAlreadyExistException();
		}
		return categoryDAO.saveCategory(categoryModel);
	}

	@Override
	public Category updateCategory(@NonNull CategoryModel categoryModel) throws CategoryNotFoundException {
		if (!categoryDAO.isCategoryExistById(categoryModel.getId())) {
			throw new CategoryNotFoundException();
		}
		return categoryDAO.updateCategory(categoryModel);
	}

	@Override
	public void deleteCategory(@NonNull Long id) throws CategoryNotFoundException {
		if (!categoryDAO.isCategoryExistById(id)) {
			throw new CategoryNotFoundException();
		}
		categoryDAO.deleteCategory(id);
	}
}
