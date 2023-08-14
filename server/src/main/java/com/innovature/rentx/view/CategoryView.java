package com.innovature.rentx.view;

import com.innovature.rentx.entity.Category;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CategoryView {
    
    private Integer id;
    private String message;
    
    public CategoryView(Category category, String message) {
        this.id = category.getId();
        this.message = message;
    }
    
    public CategoryView() {
    }

    public CategoryView(Category category) {
        this.id = category.getId();
    }
}
