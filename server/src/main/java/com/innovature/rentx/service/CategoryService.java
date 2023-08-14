package com.innovature.rentx.service;
import java.util.Collection;
import java.util.List;
import javax.validation.Valid;
import org.springframework.data.domain.Sort.Direction;
import com.innovature.rentx.form.CategoryForm;
import com.innovature.rentx.util.Pager;
import com.innovature.rentx.view.CategoryView;
import com.innovature.rentx.view.CategoryListView;

public interface CategoryService {

    CategoryView  registerCategory(@Valid CategoryForm categoryForm);

    CategoryView updateCategory(Integer id, CategoryForm categoryForm);

    CategoryView deleteCategory(Integer id);

    Pager<CategoryListView> getCategories(String search, int page, int size, String sort, Direction direction);

    Collection<CategoryListView> list();

}
