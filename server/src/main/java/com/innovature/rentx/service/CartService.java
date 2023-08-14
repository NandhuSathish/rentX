package com.innovature.rentx.service;

import com.innovature.rentx.form.CartForm;
import com.innovature.rentx.util.Pager;
import com.innovature.rentx.view.UserCartListView;
import com.innovature.rentx.view.UserCartProduListView;
import com.innovature.rentx.view.UserCartView;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

public interface CartService {

    UserCartView save(@Valid CartForm form);

    // Pager<UserCartListView>viewCartProduct(Integer page, Integer limit, String sort, String order);
    ResponseEntity<String> modifyCart(Integer cartId, Integer quantity,String startDate,String endDate);

    ResponseEntity<String> removeItem(Integer cartId);
//    Pager<UserCartProduListView>viewCartProduct(Integer page, Integer limit, String sort, String order);
    UserCartListView viewCartProduct(Integer page, Integer limit, String sort, String order);

}
