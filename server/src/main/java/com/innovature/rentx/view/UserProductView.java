package com.innovature.rentx.view;

import com.innovature.rentx.entity.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProductView {

    private Integer id;
    private String name;
    private String description;
    private Map<String, String> specification;
    private int stock;
    private int availableStock;
    private double price;
    private String coverImage;
    private String thumbnail;

    private Integer categoryId;
    private String categoryName;

    private Integer subCategoryId;
    private String subCategoryName;

    private Integer userId;
    private String userName;

    private Integer storeId;
    private String storeName;

    private Integer isAvailable;
    private Boolean isCarted;
    private Boolean isWishlisted;



public UserProductView(Product product, Integer isAvailable, Boolean isCarted, Boolean isWishlisted) {

    this.id = product.getId();
    this.name = product.getName();
    this.description = product.getDescription();
    this.specification = product.getSpecification();
    this.stock = product.getStock();
    this.availableStock = product.getAvailableStock();
    this.price = product.getPrice();
    this.coverImage = product.getCoverImage();
    this.thumbnail = product.getThumbnail();

    this.categoryId = product.getCategory().getId();
    this.categoryName = product.getCategory().getName();

    this.subCategoryId = product.getSubCategory().getId();
    this.subCategoryName = product.getSubCategory().getName();

    this.storeId = product.getStore().getId();
    this.storeName = product.getStore().getName();

    this.userId = product.getStore().getUser().getId();
    this.userName = product.getStore().getUser().getUsername();

    this.isAvailable = isAvailable;
    this.isCarted = isCarted;
    this.isWishlisted = isWishlisted;
}




}
