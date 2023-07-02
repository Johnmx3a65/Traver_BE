package com.parovsky.traver.dao.impl;

import com.parovsky.traver.dao.CategoryDAO;
import com.parovsky.traver.entity.Category;
import com.parovsky.traver.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
	public List<Category> getAll() {
		return categoryRepository.findAll();
	}

	@Override
	public List<Category> getAllFavorite(@NonNull String email) {
		return categoryRepository.findFavouriteCategories(email);
	}

	@Override
	public Optional<Category> get(@NonNull Long id) {
		return categoryRepository.findById(id);
	}

	@Override
	public boolean isExistById(@NonNull Long id) {
		return categoryRepository.existsById(id);
	}

	@Override
	public boolean isExistByName(@NonNull String name) {
		return categoryRepository.existsByName(name);
	}

	@Override
	public Category save(@NonNull Category category) {
		return categoryRepository.saveAndFlush(category);
	}

	@Override
	public void delete(@NonNull Category category) {
		categoryRepository.delete(category);
	}
}


