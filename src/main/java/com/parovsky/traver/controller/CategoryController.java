package com.parovsky.traver.controller;

import com.parovsky.traver.dto.model.SaveCategoryModel;
import com.parovsky.traver.dto.model.UpdateCategoryModel;
import com.parovsky.traver.entity.Category;
import com.parovsky.traver.exception.impl.CategoryIsAlreadyExistException;
import com.parovsky.traver.exception.impl.CategoryNotFoundException;
import com.parovsky.traver.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor(onConstructor = @__({@org.springframework.beans.factory.annotation.Autowired}))
public class CategoryController {

    private final CategoryService categoryService;

    @ResponseBody
    @GetMapping(value = "/categories")
    public List<Category> getCategories() {
        return categoryService.getAllCategories();
    }

    @ResponseBody
    @GetMapping("/categories/favorite")
    public List<Category> getFavoriteCategories() {
        return categoryService.getFavoriteCategories();
    }

    @ResponseBody
    @GetMapping(value = "/category/{id}")
    public Category getCategory(@PathVariable Long id) throws CategoryNotFoundException {
        return categoryService.getCategoryById(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/category", consumes = "application/json")
    public Category saveCategory(@RequestBody SaveCategoryModel saveCategoryModel) throws CategoryIsAlreadyExistException {
        return categoryService.saveCategory(saveCategoryModel);
    }

    @ResponseBody
    @PutMapping(value = "/category", consumes = "application/json")
    public Category updateCategory(@RequestBody UpdateCategoryModel updateCategoryModel) throws CategoryNotFoundException {
        return categoryService.updateCategory(updateCategoryModel);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/category/{id}")
    public void deleteUser(@PathVariable Long id) throws CategoryNotFoundException {
        categoryService.deleteCategory(id);
    }
}
