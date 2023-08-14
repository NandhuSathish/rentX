package com.innovature.rentx.view;

import com.innovature.rentx.util.Pager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class UserCartListView {
    Pager<UserCartProduListView> cartProducts;
    private Double totalPrice;

    public UserCartListView(Pager<UserCartProduListView> cartProducts, Double totalPrice) {
        this.cartProducts = cartProducts;
        this.totalPrice = totalPrice;
    }
}
