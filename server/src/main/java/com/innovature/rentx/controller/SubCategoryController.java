package com.innovature.rentx.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Sort;
import com.innovature.rentx.exception.BadRequestException;
import com.innovature.rentx.form.SubCategoryForm;
import com.innovature.rentx.form.SubCategoryUpdateForm;
import com.innovature.rentx.service.SubCategoryService;
import com.innovature.rentx.util.LanguageUtil;
import com.innovature.rentx.util.Pager;
import com.innovature.rentx.view.ResponseView;
import com.innovature.rentx.view.SubCategoryListView;
import com.innovature.rentx.view.AllSubCategoryListView;
import com.innovature.rentx.view.SubCategoryView;

@RestController
@RequestMapping("/subcategory")
public class SubCategoryController {

    @Autowired
    private LanguageUtil languageUtil;

    @Autowired
    private SubCategoryService subCategoryService;

    @PostMapping
    public SubCategoryView addSubCategory(@Valid @RequestBody SubCategoryForm subCategoryForm) {
        return subCategoryService.addSubCategory(subCategoryForm);
    }

    @GetMapping("/{categoryId}")
    public Pager<AllSubCategoryListView> getSubCategoriesByCategoryId(
            @PathVariable(required = false) Integer categoryId,
            @RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "7") int size, @RequestParam(defaultValue = "name") String sort,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction) {

        return subCategoryService.getSubCategoriesByCategoryId(categoryId, search, page, size, sort, direction);
    }

    

    @PutMapping("/{subCategoryId}")
    public SubCategoryView updateSubCategory(@PathVariable Integer subCategoryId,
            @Valid @RequestBody SubCategoryUpdateForm subCategoryUpdateForm) {

        return subCategoryService.updateSubCategory(subCategoryId, subCategoryUpdateForm);

    }

    @PutMapping("delete/")
    public ResponseView deleteSubCategoryRequired(@RequestParam(required = false) String category) {

        if (category == null || category.trim().isEmpty()) {
            throw new BadRequestException(
                    languageUtil.getTranslatedText("subcategoryid.queryparam.required", null, "en"));
        }
        return null;
    }

    @PutMapping("/delete/{subCategoryId}")
    public SubCategoryView deleteSubCategory(@PathVariable Integer subCategoryId) {

        return subCategoryService.deleteSubCategory(subCategoryId);

    }

    @GetMapping("/detail/{subCategoryId}")
    public SubCategoryListView subCategoryDetail(@PathVariable Integer subCategoryId) {
        return subCategoryService.subCategoryDetail(subCategoryId);
    }

}