package com.parovsky.traver.dao.impl;

import com.parovsky.traver.entity.Category;
import com.parovsky.traver.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class CategoryDAOImplTest {

	private final Long category1Id = 1L;

	private final Category category1 = Category.builder()
			.id(category1Id)
			.name("Mountains")
			.picture("pictureUrl")
			.build();

	private final Category category2 = Category.builder()
			.id(2L)
			.name("Beaches")
			.picture("pictureUrl")
			.build();

	private CategoryDAOImpl subject;

	private final List<Category> categories = new ArrayList<Category>() {{
		add(category1);
		add(category2);
	}};

	private CategoryRepository repository;

	@BeforeEach
	void setUp() {
		repository = mock(CategoryRepository.class);
		subject = new CategoryDAOImpl(repository);
	}

	@Test
	void getAllCategories() {
		doReturn(categories).when(repository).findAll();
		List<Category> categoriesResult = subject.getAllCategories();
		assertEquals(categoriesResult, categories);
	}

	@Test
	void getCategoryById() {
		doReturn(category1).when(repository).getById(category1Id);
		Category categoryResult = subject.getCategoryById(category1Id);
		assertEquals(categoryResult, category1);
	}

	@Test
	void isCategoryExistById() {
	}

	@Test
	void isCategoryExistByName() {
	}

	@Test
	void updateCategory() {
	}

	@Test
	void saveCategory() {
	}

	@Test
	void deleteCategory() {
	}
}