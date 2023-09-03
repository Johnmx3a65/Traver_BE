package com.parovsky.traver.controller;

import com.parovsky.traver.dto.form.SaveCategoryForm;
import com.parovsky.traver.dto.form.UpdateCategoryForm;
import com.parovsky.traver.entity.Category;
import com.parovsky.traver.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public List<Category> getFavoriteCategories(@AuthenticationPrincipal() UserDetails userDetails) {
        return categoryService.getFavoriteCategories(userDetails);
    }

    @ResponseBody
    @GetMapping(value = "/category/{id}")
    public Category getCategory(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/category", consumes = "application/json")
    public Category saveCategory(@Valid @RequestBody SaveCategoryForm model) {
        return categoryService.saveCategory(model);
    }

    @ResponseBody
    @PutMapping(value = "/category", consumes = "application/json")
    public Category updateCategory(@Valid @RequestBody UpdateCategoryForm model) {
        return categoryService.updateCategory(model);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/category/{id}")
    public void deleteUser(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
