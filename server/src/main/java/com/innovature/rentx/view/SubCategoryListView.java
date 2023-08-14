package com.innovature.rentx.view;
import com.innovature.rentx.entity.SubCategory;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class SubCategoryListView{
    private Integer id;
    private String name;

    public SubCategoryListView(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    public SubCategoryListView(SubCategory subCategory) {
        this.id = subCategory.getId();
        this.name = subCategory.getName();
    }
}
