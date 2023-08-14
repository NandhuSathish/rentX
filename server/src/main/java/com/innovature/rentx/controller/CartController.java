package com.innovature.rentx.controller;

import com.innovature.rentx.form.CartForm;
import com.innovature.rentx.service.CartService;
import com.innovature.rentx.util.Pager;
import com.innovature.rentx.view.UserCartListView;
import com.innovature.rentx.view.UserCartProduListView;
import com.innovature.rentx.view.UserCartView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import javax.validation.Valid;

@RestController
@RequestMapping
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("user/cart/add")
    public UserCartView cartAdd(@Valid @RequestBody CartForm form) {
        return cartService.save(form);
    }

    @GetMapping("user/cart/list")
    public UserCartListView cartList(@RequestParam(name = "page",defaultValue = "1",required = false)Integer page,
                                           @RequestParam(name = "size",defaultValue = "7",required = false)Integer limit,
                                           @RequestParam(name = "sort",defaultValue = "updatedAt") String sort,
                                           @RequestParam(name = "order",defaultValue = "DESC") String order){
        return cartService.viewCartProduct(page, limit, sort, order);
                                           }
    @PutMapping("user/cart/{cartId}")
    public ResponseEntity<String> cartModify(
            @PathVariable Integer cartId,
            @RequestParam(name = "quantity", required = false) Integer quantity,
            @RequestParam(name = "startDate", required = false) String startDate,
            @RequestParam(name = "endDate", required = false) String endDate) {
        return cartService.modifyCart(cartId, quantity, startDate, endDate);
    }

    @PutMapping("user/cart/remove/{cartId}")
    public ResponseEntity<String> removeItem(
            @PathVariable Integer cartId) {
        return cartService.removeItem(cartId);
    }

}
