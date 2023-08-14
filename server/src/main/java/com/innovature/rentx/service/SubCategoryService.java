package com.innovature.rentx.service;
import java.util.Collection;
import java.util.List;
import javax.validation.Valid;

import org.springframework.data.domain.Sort.Direction;
import com.innovature.rentx.form.SubCategoryForm;
import com.innovature.rentx.form.SubCategoryUpdateForm;
import com.innovature.rentx.util.Pager;
import com.innovature.rentx.view.AllSubCategoryListView;
import com.innovature.rentx.view.SubCategoryListView;
import com.innovature.rentx.view.SubCategoryView;
public interface SubCategoryService {

    SubCategoryView addSubCategory(SubCategoryForm subCategoryForm);

    SubCategoryView updateSubCategory(Integer subCategoryId, @Valid SubCategoryUpdateForm subCategoryUpdateForm);

    SubCategoryView deleteSubCategory(Integer id);

    Pager<AllSubCategoryListView> getSubCategoriesByCategoryId(Integer categoryId, String search, int page, int size, String sort, Direction direction);

    List<SubCategoryListView> getAllSubCategories(String search, int page, int size, String sort, Direction direction);

    SubCategoryListView subCategoryDetail(Integer subCategoryId);

    Collection<SubCategoryListView> getAllSubCategoriesUnauthorized(Integer categoryId);

    

}
