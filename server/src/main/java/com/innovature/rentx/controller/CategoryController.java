package com.innovature.rentx.controller;
import org.springframework.data.domain.Sort;

import java.util.Collection;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.innovature.rentx.form.CategoryForm;
import com.innovature.rentx.service.CategoryService;
import com.innovature.rentx.util.Pager;
import com.innovature.rentx.view.CategoryView;
import com.innovature.rentx.view.ResponseView;
import com.innovature.rentx.view.CategoryListView;
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/")
    public ResponseView registerCategory() {
        return new ResponseView("Category id is required", "3012");
    }

    @RequestMapping("/delete/")
    public ResponseView deleteCategory() {
        return new ResponseView("Category id is required", "3012");
    }
    
    @PostMapping
    public CategoryView registerCategory(@Valid @RequestBody CategoryForm categoryForm) {
        return  categoryService.registerCategory(categoryForm);
    }

    @PutMapping("/{categoryId}")
    public CategoryView updateCategory(@PathVariable Integer categoryId,@Valid @RequestBody CategoryForm categoryForm) {  
        return categoryService.updateCategory(categoryId, categoryForm);   
    }

    @PutMapping("/delete/{categoryId}")
    public CategoryView deleteCategory(@PathVariable Integer categoryId) {
       return categoryService.deleteCategory(categoryId);
    }

    @GetMapping
    public Pager<CategoryListView>  getCategories(@RequestParam(defaultValue = "") String search,@RequestParam(defaultValue = "1") int page,@RequestParam(defaultValue = "7") int size,@RequestParam(defaultValue = "name") String sort,@RequestParam(defaultValue = "ASC") Sort.Direction direction) 
    {
        return categoryService.getCategories(search, page, size, sort, direction);
    }
    
   
    
}