package com.parovsky.traver.controller;

import com.parovsky.traver.dto.model.SaveCategoryModel;
import com.parovsky.traver.dto.model.UpdateCategoryModel;
import com.parovsky.traver.entity.Category;
import com.parovsky.traver.exception.EntityAlreadyExistsException;
import com.parovsky.traver.exception.EntityNotFoundException;
import com.parovsky.traver.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public List<Category> getFavoriteCategories() throws EntityNotFoundException {
        return categoryService.getFavoriteCategories();
    }

    @ResponseBody
    @GetMapping(value = "/category/{id}")
    public Category getCategory(@PathVariable Long id) throws EntityNotFoundException {
        return categoryService.getCategoryById(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/category", consumes = "application/json")
    public Category saveCategory(@Valid @RequestBody SaveCategoryModel model) throws EntityAlreadyExistsException {
        return categoryService.saveCategory(model);
    }

    @ResponseBody
    @PutMapping(value = "/category", consumes = "application/json")
    public Category updateCategory(@Valid @RequestBody UpdateCategoryModel model) throws EntityNotFoundException {
        return categoryService.updateCategory(model);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/category/{id}")
    public void deleteUser(@PathVariable Long id) throws EntityNotFoundException {
        categoryService.deleteCategory(id);
    }
}
