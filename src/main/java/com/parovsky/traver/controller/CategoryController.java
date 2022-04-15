package com.parovsky.traver.controller;

import com.parovsky.traver.dto.CategoryDTO;
import com.parovsky.traver.exception.impl.CategoryIsAlreadyExistException;
import com.parovsky.traver.exception.impl.CategoryNotFoundException;
import com.parovsky.traver.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value = "/categories")
    public ResponseEntity<List<CategoryDTO>> getCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping(value = "/category/{id}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable Long id) throws CategoryNotFoundException {
        CategoryDTO categoryDTO = categoryService.getCategoryById(id);
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/category", consumes = "application/json")
    public ResponseEntity<CategoryDTO> saveCategory(@RequestBody CategoryDTO categoryDTO) throws CategoryIsAlreadyExistException {
        CategoryDTO category = categoryService.saveCategory(categoryDTO);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PutMapping(value = "/category", consumes = "application/json")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO) throws CategoryNotFoundException {
        CategoryDTO category = categoryService.updateCategory(categoryDTO);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping(value = "/category/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws CategoryNotFoundException {
        categoryService.deleteCategory(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
