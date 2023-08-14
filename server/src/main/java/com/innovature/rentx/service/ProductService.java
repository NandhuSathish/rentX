package com.innovature.rentx.service;

import javax.validation.Valid;

import com.innovature.rentx.view.UserProductDetailView;
import org.springframework.http.ResponseEntity;

import com.innovature.rentx.form.ImageProductForm;
import com.innovature.rentx.form.ProductForm;
import com.innovature.rentx.util.Pager;
import com.innovature.rentx.view.ProductDetailView;
import com.innovature.rentx.view.ProductView;
import com.innovature.rentx.view.UserProductView;

public interface ProductService {

    ProductView createProduct(ProductForm form);

    Pager<UserProductView> viewProduct(String searchData, Integer page, Integer limit, String sort, String order, String CategoryId, String StoreId);

    Pager<UserProductView> vendorViewProduct(String searchData, Integer page, Integer limit, String sort, String order, String CategoryId, String StoreId);



    ResponseEntity<String> addImageProduct(@Valid ImageProductForm form);

    ResponseEntity<String> deleteProduct(Integer productId);

    ProductDetailView detailView(Integer productId);

    UserProductDetailView userProductDetailView(Integer productId);
    
}
