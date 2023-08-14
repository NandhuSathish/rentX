package com.innovature.rentx.controller;

import com.innovature.rentx.entity.User;
import com.innovature.rentx.exception.BadRequestException;
import com.innovature.rentx.form.GoogleSignInForm;
import com.innovature.rentx.form.UserForm;
import com.innovature.rentx.service.ProductService;
import com.innovature.rentx.service.UserService;
import com.innovature.rentx.util.Pager;
import com.innovature.rentx.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    // user reg
    @PostMapping("/add")
    public EmailTokenView add(@Valid @RequestBody UserForm form) {
        return userService.add(form, User.Role.USER.value);
    }

    // google authenticatioin
    @PostMapping("/googleAuth")
    public UserView googleAuth(@Valid @RequestBody GoogleSignInForm form)
            throws BadRequestException, GeneralSecurityException, IOException {
        return userService.googleAuth(form, User.Role.USER.value);
    }

    @GetMapping("/product/list")
    public Pager<UserProductView> productList(
            @RequestParam(name = "search", defaultValue = "", required = false) String searchData,
            @RequestParam(name = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "15", required = false) Integer limit,
            @RequestParam(name = "sort", defaultValue = "name") String sort,
            @RequestParam(name = "order", defaultValue = "ASC") String order,
            @RequestParam(name = "category", required = false) String category,
            @RequestParam(name = "store", required = false) String store) {
        return productService.viewProduct(searchData, page, limit, sort, order, category, store);
    }

    @GetMapping("/product/{productId}")
    public UserProductDetailView userProductView(@PathVariable Integer productId){
        return productService.userProductDetailView(productId);
    }

}
