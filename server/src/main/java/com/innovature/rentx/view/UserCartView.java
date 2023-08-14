package com.innovature.rentx.view;

import com.innovature.rentx.entity.Cart;
import com.innovature.rentx.entity.Product;
import com.innovature.rentx.form.validaton.ProductAnotation;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserCartView {
    private Product product;
    private Integer quantity;
    private Date startDate;
    private Date endDate;
    private Integer cartCount;


    public UserCartView(Cart cart,Integer cartCount){
        this.product=cart.getProduct();
        this.quantity= cart.getQuantity();
        this.startDate=cart.getStartDate();
        this.endDate=cart.getEndDate();
        this.cartCount=cartCount;
    }
}
