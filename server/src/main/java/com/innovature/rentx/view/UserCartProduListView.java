package com.innovature.rentx.view;

import com.innovature.rentx.entity.Cart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCartProduListView {

    private Integer id;
    private Integer quantity;
    private Date startDate;
    private Date endDate;
    private Date createdAt;
    private Date updatedAt;
    private Boolean isAvailable;
    private String productName;
    private double price;
    private String coverImage;
    private String thumbnail;
    private String categoryName;
    private Integer productQuantity;
    private String storeName;

//    private Double totalPrice;


    public UserCartProduListView(Cart cart){
        this.id=cart.getId();
        this.quantity=cart.getQuantity();
        this.startDate=cart.getStartDate();
        this.endDate=cart.getEndDate();
        this.createdAt=cart.getCreatedAt();
        this.updatedAt=cart.getUpdatedAt();
        this.productName=cart.getProduct().getName();
        this.productQuantity=cart.getProduct().getAvailableStock();
        this.price=cart.getProduct().getPrice();
        this.coverImage=cart.getProduct().getCoverImage();
        this.thumbnail=cart.getProduct().getThumbnail();
        this.categoryName=cart.getProduct().getCategory().getName();
        this.storeName=cart.getProduct().getStore().getName();
//        this.totalPrice=getTotalPrice();

        
    }


}
