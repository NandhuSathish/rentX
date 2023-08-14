package com.innovature.rentx.controller;


import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innovature.rentx.service.CategoryService;
import com.innovature.rentx.service.StoreService;
import com.innovature.rentx.service.SubCategoryService;
import com.innovature.rentx.view.CategoryListView;
import com.innovature.rentx.view.StoreView;
import com.innovature.rentx.view.SubCategoryListView;

@RestController
@RequestMapping("/unAuthorized")
public class UnauthorizedController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private StoreService storeService;
    @Autowired
    private SubCategoryService subCategoryService;
    @GetMapping("/category/findAll")
    public Collection<CategoryListView> listCategory() {
        return categoryService.list();
    }
    @GetMapping("store/findAll")
    public Collection<StoreView> listStore() {
        return storeService.list();
    }
    @GetMapping("/subCategory/{categoryId}")
    public Collection<SubCategoryListView> getAllSubCategories(@PathVariable(required = false) Integer categoryId) {

        return subCategoryService.getAllSubCategoriesUnauthorized(categoryId);
    }
}
