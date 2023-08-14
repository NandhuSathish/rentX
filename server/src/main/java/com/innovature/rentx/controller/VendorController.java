package com.innovature.rentx.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.validation.Valid;

import com.innovature.rentx.form.*;
import com.innovature.rentx.service.ProductService;
import com.innovature.rentx.service.StoreService;
import com.innovature.rentx.util.LanguageUtil;
import com.innovature.rentx.util.Pager;
import com.innovature.rentx.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.innovature.rentx.entity.User;
import com.innovature.rentx.exception.BadRequestException;
import com.innovature.rentx.service.UserService;
import com.innovature.rentx.service.VendorService;

@RestController
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    protected UserService userService;

    @Autowired
    private LanguageUtil languageUtil;

    @Autowired
    private StoreService storeService;

    @Autowired
    private VendorService vendorService;

    @Autowired
    private ProductService productService;

    @PostMapping("/googleAuth")
    public UserView googleAuth(@Valid @RequestBody GoogleSignInForm form)
            throws BadRequestException, GeneralSecurityException, IOException {
        return userService.googleAuth(form, User.Role.VENDOR.value);
    }

    // initial reg
    @PostMapping("/registration")
    public EmailTokenView add(@Valid @RequestBody UserForm form) {
        return userService.add(form, User.Role.VENDOR.value);
    }

    protected UserService userService1;
    // OTP verify
    @PostMapping("/otp/verify")
    public VendorOtpView verifyVendorOtp(@Valid @RequestBody OtpForm form) {
        return userService.verifyVendorOtp(form);
    }

    // stage1
    @PutMapping("/registration")
    public VendorRegStage1View vendorRegStage1(@Valid @RequestBody VendorRegStage1Form form) {
        return userService.vendorRegStage1(form);
    }

    // stage2
    @PostMapping("/bankDetails")
    public VendorDetailsView add(@Valid @RequestBody VendorDetailsForm form) {
        return vendorService.add(form);
    }

    // Add store
    @PostMapping("store/add")
    public ResponseEntity<String> addStore(@Valid @RequestBody StoreForm form) {

        storeService.addStore(form);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("store/list")
    public Pager<VendorStoreListView> vendorStoreListViewPager(
            @RequestParam(name = "search", defaultValue = "", required = false) String search,
            @RequestParam(name = "page", defaultValue = "1", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "5", required = false) Integer size,
            @RequestParam(name = "sort", defaultValue = "updatedAt") String sort,
            @RequestParam(name = "direction", defaultValue = "DESC") String direction) {
        return storeService.listStore(search, page, size, sort, direction);
    }

    
    @PostMapping("product/add")
    public ProductView createProduct(@Valid @RequestBody ProductForm form) {
        return productService.createProduct(form);
    }

    @PostMapping("product/addImage")
    public ResponseEntity<String> createProduct(@Valid @RequestBody ImageProductForm form) {
        return productService.addImageProduct(form);
    }

    @PutMapping("product/delete/{productId}")
    public  ResponseEntity<String> deleteProduct(@PathVariable Integer productId) {
    return productService.deleteProduct(productId);
    }   

    @GetMapping("product/detailView/{productId}")
    public  ProductDetailView detailView(@PathVariable Integer productId) {
    return productService.detailView(productId);
    }

    @GetMapping("/product/list")
    public Pager<UserProductView> productList(@RequestParam(name ="search",defaultValue="" ,required = false) String searchData,
                                              @RequestParam(name = "page",defaultValue = "1",required = false)Integer page,
                                              @RequestParam(name = "size",defaultValue = "7",required = false)Integer limit,
                                              @RequestParam(name = "sort",defaultValue = "name") String sort,
                                              @RequestParam(name = "order",defaultValue = "ASC") String order,
                                              @RequestParam(name="category",required = false) String category,
                                              @RequestParam(name="store",required = false) String store){
        return productService.vendorViewProduct(searchData,page,limit,sort,order,category,store);
    }

    public void setUserService(UserService userService2) {
      // TODO document why this method is empty
    }

    public void setLanguageUtil(LanguageUtil languageUtil2) {
      // TODO document why this method is empty
    }

    public void setStoreService(StoreService storeService2) {
      // TODO document why this method is empty
    }

    public void setVendorService(VendorService vendorService2) {
      // TODO document why this method is empty
    }

    public void setProductService(ProductService productService2) {
      // TODO document why this method is empty
    }

}