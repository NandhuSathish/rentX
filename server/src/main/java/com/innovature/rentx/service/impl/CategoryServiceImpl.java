package com.innovature.rentx.service.impl;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import com.innovature.rentx.entity.Category;
import com.innovature.rentx.entity.SubCategory;
import com.innovature.rentx.entity.Category.STATUS;
import com.innovature.rentx.exception.ConflictException;
import com.innovature.rentx.form.CategoryForm;
import com.innovature.rentx.repository.CategoryRepository;
import com.innovature.rentx.repository.SubCategoryRepository;
import com.innovature.rentx.service.CategoryService;
import com.innovature.rentx.util.CategoryUtil;
import com.innovature.rentx.util.LanguageUtil;
import com.innovature.rentx.util.Pager;
import com.innovature.rentx.view.CategoryView;
import com.innovature.rentx.view.CategoryListView;

@Service
public class CategoryServiceImpl implements CategoryService {
    private static final String SUCCESSFULLY_UPDATED ="successfully updated";
    private static final String SUCCESSFULLY_CREATED ="successfully created";
    private static final String SUCCESSFULLY_DELETED ="successfully deleted";
    private static final String CATEGORY_ID_REQUIRED ="categoryid.queryparam.required";
    private static final String CATEGORY_NAME_DUPLICATE="category.name.duplicate";
    private static final String CATEGORY_NOT_EXIST="category.name.nonexist";
    private static final String ALREADY_DELETED="category.id.alreadydeleted";
    private static final String INVALID_PAGE_NUMBER = "error.page.number.natural";
    private static final String INVALID_PAGE_SIZE ="error.page.size.natural";
    private static final String INVALID_SORT_VALUE ="error.sort.value.invalid";
    private static final String INVALID_SORT_DIRECTION ="error.sort.direction.invalid";
    @Autowired
    private CategoryUtil categoryUtil;
    @Autowired
    private LanguageUtil languageUtil;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SubCategoryRepository subCategoryRepository;

    
    //Add Category - category name is unique
    public CategoryView registerCategory(CategoryForm categoryForm) {
        categoryUtil.checkDuplicateCategoryName(categoryForm.getName(),CATEGORY_NAME_DUPLICATE);
        Category category = new Category();
        category.setName(categoryForm.getName());
        category.setCoverImage(categoryForm.getCoverImage());
        categoryRepository.save(category);
        return new CategoryView(category,SUCCESSFULLY_CREATED);
    }
    

    //Update Category
    public CategoryView updateCategory(Integer id, CategoryForm categoryForm) {
        categoryUtil.requiredCheck(id,CATEGORY_ID_REQUIRED);
        Category category = categoryRepository.findByIdAndStatus(id, STATUS.ACTIVE.value).orElseThrow(() ->  new ConflictException(languageUtil.getTranslatedText(CATEGORY_NOT_EXIST, null, "en")));
        categoryUtil.checkDuplicateCategoryName(categoryForm.getName(),CATEGORY_NAME_DUPLICATE);
        category.setName(categoryForm.getName());    
        categoryRepository.save(category);
        return new CategoryView(category,SUCCESSFULLY_UPDATED);
    }

    // Delete Category - Category and its Corresponding Subcategories are Soft Deleted
    @Transactional
    public CategoryView deleteCategory(Integer id) {
        categoryUtil.requiredCheck(id,CATEGORY_ID_REQUIRED);
        categoryUtil.checkCategoryAlreadyDeleted(id, ALREADY_DELETED);
        Category category = categoryRepository.findByIdAndStatus(id,STATUS.ACTIVE.value).orElseThrow(() -> new ConflictException(languageUtil.getTranslatedText(CATEGORY_NOT_EXIST, null, "en")));
        category.setStatus(STATUS.DELETED.value);
        categoryRepository.save(category);
        List<SubCategory> subCategories = subCategoryRepository.findByCategoryIdAndStatus(id, STATUS.ACTIVE.value);
        subCategories.forEach(subCategory -> subCategory.setStatus(STATUS.DELETED.value));
        subCategoryRepository.saveAll(subCategories);
        return new CategoryView(category,SUCCESSFULLY_DELETED);
    }

    public Pager<CategoryListView> getCategories(String name, int pageNumber, int pageSize, String sortField, Sort.Direction sortDirection) {
        categoryUtil.checkListParams(pageNumber,pageSize,sortField,sortDirection,INVALID_PAGE_NUMBER,INVALID_PAGE_SIZE,INVALID_SORT_VALUE,INVALID_SORT_DIRECTION);
        Pager<CategoryListView>viewCategory;
        List<CategoryListView>categoryList;
        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize, sortDirection, sortField);
        categoryList = categoryRepository.findByNameContainingAndStatus(name, STATUS.ACTIVE.value, pageRequest)
        .stream().map(CategoryListView::new).collect(Collectors.toList());
        
        int totalCount=categoryRepository.countByNameContainingAndStatus(name,STATUS.ACTIVE.value);
        viewCategory=new Pager<>(pageSize,totalCount,pageNumber);
        viewCategory.setResult(categoryList);
        return viewCategory;
      }


    @Override
    public Collection<CategoryListView> list() {
        Iterable<Category> iterableCategories = categoryRepository.findAllByStatus(STATUS.ACTIVE.value);
    
        // Map Category objects to CategoryListView objects using Stream API and Collectors.toList()
        List<CategoryListView> categoryListViews = StreamSupport.stream(iterableCategories.spliterator(), false)
                                                               .map(category -> new CategoryListView(category))
                                                               .collect(Collectors.toList());
        return categoryListViews;
    }
    

    
    
  
    
}
