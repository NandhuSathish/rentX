package com.innovature.rentx.service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import com.innovature.rentx.entity.Category;
import com.innovature.rentx.entity.SubCategory;
import com.innovature.rentx.entity.SubCategory.STATUS;
import com.innovature.rentx.exception.ConflictException;
import com.innovature.rentx.form.SubCategoryForm;
import com.innovature.rentx.form.SubCategoryUpdateForm;
import com.innovature.rentx.repository.CategoryRepository;
import com.innovature.rentx.repository.SubCategoryRepository;
import com.innovature.rentx.service.SubCategoryService;
import com.innovature.rentx.util.CategoryUtil;
import com.innovature.rentx.util.LanguageUtil;
import com.innovature.rentx.util.Pager;
import com.innovature.rentx.view.AllSubCategoryListView;
import com.innovature.rentx.view.SubCategoryListView;
import com.innovature.rentx.view.SubCategoryView;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {
    private static final String CATEGORY_ID = "category.id.required";
    private static final String CATEGORY_ID_NUMERIC ="category.id.numeric";
    private static final String DUPLICATE_SUB_CATEGORY_NAME ="subcategory.name.duplicate";
    private static final String CATEGORY_ADD ="successfully created";
    private static final String SUBCATEGORY_ID_REQUIRED="subcategoryid.queryparam.required";
    private static final String CATEGORY_NOT_EXIST="category.name.nonexist";
    private static final String SUBCATEGORY_DOESNT_EXIST="subcategory.name.nonexist";
    private static final String SUCCESSFULLY_UPDATED="successfully updated";
    private static final String SUCCESSFULLY_DELETED="successfully deleted";
    private static final String INVALID_PAGE_NUMBER = "error.page.number.natural";
    private static final String INVALID_PAGE_SIZE ="error.page.size.natural";
    private static final String INVALID_SORT_VALUE ="error.sort.value.invalid";
    private static final String INVALID_SORT_DIRECTION ="error.sort.direction.invalid";
    private static final String ALREADY_DELETED="subcategory.id.alreadydeleted";

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryUtil categoryUtil;

    @Autowired
    private LanguageUtil languageUtil;

    @Override
    public SubCategoryView addSubCategory(@Valid SubCategoryForm subCategoryForm) 
    {
        categoryUtil.categoryIdNumericCheck(subCategoryForm.getCategoryId(), CATEGORY_ID_NUMERIC);
        Integer categoryId = Integer.parseInt(subCategoryForm.getCategoryId());
        categoryUtil.requiredCheck(categoryId, CATEGORY_ID);
        Category category = categoryRepository.findByIdAndStatus(categoryId, STATUS.ACTIVE.value).orElseThrow(() -> new ConflictException(languageUtil.getTranslatedText(CATEGORY_NOT_EXIST, null, "en")));
        categoryUtil.CheckDuplicateSubCategoryName(subCategoryForm.getName(),DUPLICATE_SUB_CATEGORY_NAME);
        SubCategory subCategory = new SubCategory();
        subCategory.setName(subCategoryForm.getName());
        subCategory.setCategory(category);
        subCategoryRepository.save(subCategory);
        return new SubCategoryView(subCategory,CATEGORY_ADD);
    }

    @Override
    public SubCategoryView updateSubCategory(Integer subCategoryId, @Valid SubCategoryUpdateForm subCategoryUpdateForm)
    {
        categoryUtil.requiredCheck(subCategoryId,SUBCATEGORY_ID_REQUIRED);
        SubCategory subCategory=subCategoryRepository.findByIdAndStatus(subCategoryId,STATUS.ACTIVE.value).orElseThrow(()->new ConflictException(languageUtil.getTranslatedText(SUBCATEGORY_DOESNT_EXIST,null,"en")));
        categoryUtil.CheckDuplicateSubCategoryName(subCategoryUpdateForm.getName(),DUPLICATE_SUB_CATEGORY_NAME);
        subCategory.setName(subCategoryUpdateForm.getName());
        subCategoryRepository.save(subCategory);
        return new SubCategoryView(subCategory, SUCCESSFULLY_UPDATED);
    }


    @Override
    public SubCategoryView deleteSubCategory(Integer id) 
    {
        categoryUtil.requiredCheck(id,SUBCATEGORY_ID_REQUIRED);
        categoryUtil.checkSubCategoryAlreadyDeleted(id, ALREADY_DELETED);
        SubCategory subCategory=subCategoryRepository.findByIdAndStatus(id,STATUS.ACTIVE.value).orElseThrow(()->new ConflictException(languageUtil.getTranslatedText(SUBCATEGORY_DOESNT_EXIST,null,"en")));
        subCategory.setStatus(STATUS.DELETED.value);
        subCategoryRepository.save(subCategory);
        return new SubCategoryView(subCategory,SUCCESSFULLY_DELETED);
    }


    public Pager<AllSubCategoryListView> getSubCategoriesByCategoryId(Integer id, String search, int page, int size, String sort, Direction direction) {
        categoryUtil.checkListParams(page, size, sort, direction, INVALID_PAGE_NUMBER, INVALID_PAGE_SIZE, INVALID_SORT_VALUE, INVALID_SORT_DIRECTION);
        
        Category category = categoryRepository.findByIdAndStatus(id, STATUS.ACTIVE.value)
                .orElseThrow(() -> new ConflictException(languageUtil.getTranslatedText(CATEGORY_NOT_EXIST, null, "en")));
        
        PageRequest pageable = PageRequest.of(page-1, size, direction, sort);
        Page<SubCategory> subCategoriesPage = subCategoryRepository.findByCategoryIdAndStatusAndNameContainingIgnoreCase(id, STATUS.ACTIVE.value, search, pageable);
        
        List<SubCategoryListView> subCategoryListViews = subCategoriesPage.getContent()
                .stream()
                .map(SubCategoryListView::new)
                .collect(Collectors.toList());
        
        AllSubCategoryListView allSubCategoryListView = new AllSubCategoryListView(category.getName(), subCategoryListViews);
        
        int totalCount = subCategoryRepository.countByNameContainingIgnoreCaseAndCategoryIdAndStatus(search,id,STATUS.ACTIVE.value);
        
        Pager<AllSubCategoryListView> viewSubCategories = new Pager<>(size, totalCount, page);
        viewSubCategories.setResult(Collections.singletonList(allSubCategoryListView));
        
        return viewSubCategories;
    }
    
    
    @Override
    public List<SubCategoryListView> getAllSubCategories(String search, int page, int size, String sort,Direction direction) 
    {
        categoryUtil.checkListParams(page,size,sort,direction,INVALID_PAGE_NUMBER,INVALID_PAGE_SIZE,INVALID_SORT_VALUE,INVALID_SORT_DIRECTION);
        Pageable pageable = PageRequest.of(page-1, size, direction, sort);
        return subCategoryRepository.findByNameContainingAndStatus(search,STATUS.ACTIVE.value, pageable);
    }
    @Override
    public SubCategoryListView subCategoryDetail(Integer subCategoryId) {
        categoryUtil.requiredCheck(subCategoryId, SUBCATEGORY_ID_REQUIRED);
        SubCategory subCategory = subCategoryRepository.findByIdAndStatus(subCategoryId, STATUS.ACTIVE.value)
                .orElseThrow(() -> new ConflictException(languageUtil.getTranslatedText(SUBCATEGORY_DOESNT_EXIST, null, "en")));
        
        return new SubCategoryListView(subCategory);
    }

    @Override
    public Collection<SubCategoryListView> getAllSubCategoriesUnauthorized(Integer categoryId) {
        Iterable<SubCategory> iterableSubcategories = subCategoryRepository.findByCategoryIdAndStatus(categoryId,STATUS.ACTIVE.value);
    
        // Map Category objects to CategoryListView objects using Stream API and Collectors.toList()
        List<SubCategoryListView> subCategoryListViews = StreamSupport.stream(iterableSubcategories.spliterator(), false)
                                                               .map(subCategory -> new SubCategoryListView(subCategory))
                                                               .collect(Collectors.toList());
        return subCategoryListViews;
       }

    
}
