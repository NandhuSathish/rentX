package com.innovature.rentx.view;

import com.innovature.rentx.entity.Category;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CategoryDetailView {
    
    private Integer id;
    private String name;
    
    public CategoryDetailView(Category category)
    {
        this.id = category.getId();
        this.name = category.getName();
    }

    public CategoryDetailView() {
    }
}