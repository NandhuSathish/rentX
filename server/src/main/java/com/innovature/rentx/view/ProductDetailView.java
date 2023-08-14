package com.innovature.rentx.view;


import java.util.Map;

import com.innovature.rentx.entity.Category;
import com.innovature.rentx.entity.ImageProduct;
import com.innovature.rentx.entity.Product;
import com.innovature.rentx.entity.Store;
import com.innovature.rentx.entity.SubCategory;

import lombok.Getter;
@Getter
public class ProductDetailView {
 
    private Integer id;
    private String name;   
    private String description;
    private Map<String, String> specification;
    private int stock;
    private int availableStock;
    private double price;
    private String coverImage;
    private String thumbnail;


    //image table

    private String image1;
    private String image2;
    private String image3;
    private String image4;

    
    private Category category;
    private SubCategory subCategory;
    private Store store;
    private int categoryId;
    


   

    public ProductDetailView() {
    }

    public ProductDetailView(Product product,ImageProduct image) {

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

        this.image1=image.getImage1();
        this.image2=image.getImage2();
        this.image3=image.getImage3();
        this.image4=image.getImage4();

        
    }

    public ProductDetailView(Product product) {

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

        // this.image1=image.getImage1();
        // this.image2=image.getImage2();
        // this.image3=image.getImage3();
        // this.image4=image.getImage4();

        
    }

}
