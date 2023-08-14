package com.innovature.rentx.view;
import com.innovature.rentx.entity.SubCategory;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SubCategoryView {
    
    private Integer id;
    private String message;
    
    public SubCategoryView(SubCategory subCategory, String message) {
        this.id = subCategory.getId();
        this.message = message;
    }
    
    public SubCategoryView() {
    }
}