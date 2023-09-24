package com.parovsky.traver.service.impl;

import com.parovsky.traver.dto.form.SaveCategoryForm;
import com.parovsky.traver.dto.form.UpdateCategoryForm;
import com.parovsky.traver.entity.Category;
import com.parovsky.traver.exception.ApplicationException;
import com.parovsky.traver.repository.CategoryRepository;
import com.parovsky.traver.repository.LocationRepository;
import com.parovsky.traver.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

import static com.parovsky.traver.exception.Errors.*;
import static com.parovsky.traver.utils.Constraints.ID;
import static com.parovsky.traver.utils.Constraints.NAME;

@Service
@AllArgsConstructor(onConstructor = @__({@org.springframework.beans.factory.annotation.Autowired}))
public class CategoryServiceImpl implements CategoryService {

	private final CategoryRepository categoryRepository;

	private final LocationRepository locationRepository;

	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public List<Category> getFavoriteCategories(UserDetails userDetails) {
		return categoryRepository.findAllByLocationsFollowersEmail(userDetails.getUsername());
	}

	@Override
	public Category getCategoryById(@NonNull Long id) {
		return categoryRepository.findById(id).orElseThrow(() -> new ApplicationException(CATEGORY_NOT_FOUND, Collections.singletonMap(ID, id)));
	}

	@Override
	public Category saveCategory(@NonNull SaveCategoryForm model) {
		if (categoryRepository.existsByName(model.getName())) {
			throw new ApplicationException(CATEGORY_ALREADY_EXIST, Collections.singletonMap(NAME, model.getName()));
		}
		Category category = Category.builder()
				.name(model.getName())
				.picture(model.getPicture())
				.build();
		return categoryRepository.saveAndFlush(category);
	}

	@Override
	public Category updateCategory(@Valid @NonNull UpdateCategoryForm model) {
		Category category = categoryRepository.findById(model.getId()).orElseThrow(() -> new ApplicationException(CATEGORY_NOT_FOUND, Collections.singletonMap(ID, model.getId())));
		if (categoryRepository.existsByName(model.getName())) {
			throw new ApplicationException(CATEGORY_ALREADY_EXIST, Collections.singletonMap(NAME, model.getName()));
		}
		category.setName(model.getName());
		category.setPicture(model.getPicture());
		return categoryRepository.saveAndFlush(category);
	}

	@Override
	public void deleteCategory(@NonNull Long id) {
		Category category = categoryRepository.findById(id).orElseThrow(() -> new ApplicationException(CATEGORY_NOT_FOUND, Collections.singletonMap(ID, id)));
		if (locationRepository.existsByCategoryId(id)) {
			throw new ApplicationException(CATEGORY_HAS_LOCATIONS, Collections.singletonMap(ID, id));
		}
		categoryRepository.delete(category);
	}
}
