package com.parovsky.traver.controller;

import com.parovsky.traver.dto.CategoryDTO;
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
    public List<CategoryDTO> getCategories() {
        return categoryService.getAllCategories();
    }

    @ResponseBody
    @GetMapping("/categories/favorite")
    public List<CategoryDTO> getFavoriteCategories() {
        return categoryService.getFavoriteCategories();
    }

    @ResponseBody
    @GetMapping(value = "/category/{id}")
    public CategoryDTO getCategory(@PathVariable Long id) throws CategoryNotFoundException {
        return categoryService.getCategoryById(id);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/category", consumes = "application/json")
    public CategoryDTO saveCategory(@RequestBody CategoryDTO categoryDTO) throws CategoryIsAlreadyExistException {
        return categoryService.saveCategory(categoryDTO);
    }

    @ResponseBody
    @PutMapping(value = "/category", consumes = "application/json")
    public CategoryDTO updateCategory(@RequestBody CategoryDTO categoryDTO) throws CategoryNotFoundException {
        return categoryService.updateCategory(categoryDTO);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/category/{id}")
    public void deleteUser(@PathVariable Long id) throws CategoryNotFoundException {
        categoryService.deleteCategory(id);
    }
}
