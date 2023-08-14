package com.innovature.rentx.view;

import java.util.Map;

import com.innovature.rentx.entity.Category;
import com.innovature.rentx.entity.Product;
import com.innovature.rentx.entity.Store;
import com.innovature.rentx.entity.SubCategory;

import lombok.Getter;
@Getter

public class ProductView {

    private Integer id;
    private String name;   
    private String description;
    private Map<String, String> specification;
    private int stock;
    private int availableStock;
    private double price;
    private String coverImage;
    private String thumbnail;
    
    private Category category;
    private SubCategory subCategory;
    private Store store;
    private int categoryId;
   

    public ProductView() {
    }

    public ProductView(Product product) {

        this.id = product.getId();
        this.name = product.getName();
        this.stock = product.getStock();
        this.availableStock = product.getAvailableStock();
        this.price = product.getPrice();
        this.category = product.getCategory();
        this.subCategory = product.getSubCategory();
        this.store = product.getStore();
        this.description = product.getDescription();
        this.stock = product.getStock();
        this.availableStock = product.getAvailableStock();
        this.price = product.getPrice();
        this.specification = product.getSpecification();
        this.coverImage=product.getCoverImage();
        this.thumbnail=product.getThumbnail();
    }

}
