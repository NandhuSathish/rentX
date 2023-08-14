package com.innovature.rentx.view;

import com.innovature.rentx.entity.Category;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CategoryListView {
    
    private Integer id;
    private String name;
    
    public CategoryListView(Category category)
    {
        this.id = category.getId();
        this.name = category.getName();
    }

    public CategoryListView() {
    }
}