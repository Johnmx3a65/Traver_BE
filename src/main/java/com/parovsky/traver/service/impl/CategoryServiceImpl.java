package com.parovsky.traver.service.impl;

import com.parovsky.traver.dao.CategoryDAO;
import com.parovsky.traver.dto.model.CategoryModel;
import com.parovsky.traver.dto.view.UserView;
import com.parovsky.traver.entity.Category;
import com.parovsky.traver.exception.impl.CategoryIsAlreadyExistException;
import com.parovsky.traver.exception.impl.CategoryNotFoundException;
import com.parovsky.traver.exception.impl.UserNotFoundException;
import com.parovsky.traver.service.CategoryService;
import com.parovsky.traver.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__({@org.springframework.beans.factory.annotation.Autowired}))
public class CategoryServiceImpl implements CategoryService {

	private final CategoryDAO categoryDAO;

	private final UserService userService;

	@Override
	public List<Category> getAllCategories() {
		return categoryDAO.getAll();
	}

	@Override
	public List<Category> getFavoriteCategories() throws UserNotFoundException {
		UserView user = userService.getCurrentUser();
		return categoryDAO.getAllFavorite(user.getEmail());
	}

	@Override
	public Category getCategoryById(@NonNull Long id) throws CategoryNotFoundException {
		return categoryDAO.get(id).orElseThrow(CategoryNotFoundException::new);
	}

	@Override
	public Category saveCategory(@NonNull CategoryModel categoryModel) throws CategoryIsAlreadyExistException {
		if (categoryDAO.isExistByName(categoryModel.getName())) {
			throw new CategoryIsAlreadyExistException();
		}
		Category category = Category.builder()
				.name(categoryModel.getName())
				.picture(categoryModel.getPicture())
				.build();
		return categoryDAO.save(category);
	}

	@Override
	public Category updateCategory(@NonNull CategoryModel categoryModel) throws CategoryNotFoundException {
		Category category = categoryDAO.get(categoryModel.getId()).orElseThrow(CategoryNotFoundException::new);
		category.setName(categoryModel.getName());
		category.setPicture(categoryModel.getPicture());
		return categoryDAO.save(category);
	}

	@Override
	public void deleteCategory(@NonNull Long id) throws CategoryNotFoundException {
		Category category = categoryDAO.get(id).orElseThrow(CategoryNotFoundException::new);
		categoryDAO.delete(category);
	}
}
