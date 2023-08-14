package com.innovature.rentx.controller;

import com.innovature.rentx.form.*;
import com.innovature.rentx.service.AdminService;
import com.innovature.rentx.util.Pager;
import com.innovature.rentx.view.AdminLoginView;
import com.innovature.rentx.view.AdminVendorView;
import com.innovature.rentx.view.EmailTokenView;
import com.innovature.rentx.view.RefreshTokenView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/add")
    public ResponseEntity<String>add(@Valid @RequestBody AdminForm form){

        return adminService.add(form);
    }

    @PostMapping("/login")
    public AdminLoginView login(@Valid @RequestBody LoginForm form){
        return adminService.login(form);
    }


    @PostMapping("/forgetPassword")
    public EmailTokenView forgetemail(@Valid @RequestBody EmailForm form){
        return adminService.forgetPasswordEmail(form);
    }
    @PutMapping("/login")
    public RefreshTokenView refresh(@RequestBody RefreshTokenForm form){
        return adminService.refresh(form);
    }

    @PostMapping("otp/verify/forgetpassword")
    public EmailTokenView forgetverify(@Valid @RequestBody OtpForm form) {

        return adminService.forgetVerify(form);
    }
    @PutMapping("/changePassword")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordForm form){
        return adminService.changePassword(form);
    }
    @PutMapping("/vendor/approve/{id}")
    public ResponseEntity<String> approveReject( @PathVariable("id") Integer id ,@RequestParam Integer btnValue){
        return adminService.approveReject(id, btnValue);
    }

    @GetMapping("/vendor/list")
    public Pager<AdminVendorView>vendorList(@RequestParam(name ="searchData",defaultValue="" ,required = false) String searchData,
                                            @RequestParam(name = "page",defaultValue = "1",required = false)Integer page,
                                            @RequestParam(name = "size",defaultValue = "7",required = false)Integer limit,
                                            @RequestParam(name = "sort",defaultValue = "updatedAt") String sort,
                                            @RequestParam(name = "order",defaultValue = "DESC") String order,
                                            @RequestParam(defaultValue = "1") Integer statusValue){
        return adminService.listVendor(searchData,page,limit,sort,order,statusValue);

    }

    @GetMapping("/user/list")
    public Pager<AdminVendorView>userList(@RequestParam(name ="search",defaultValue="" ,required = false) String searchData,
                                          @RequestParam(name = "page",defaultValue = "1",required = false)Integer page,
                                          @RequestParam(name = "size",defaultValue = "7",required = false)Integer limit,
                                          @RequestParam(name = "sort",defaultValue = "updatedAt") String sort,
                                          @RequestParam(name = "order",defaultValue = "DESC") String order){
        return adminService.listUser(searchData,page,limit,sort,order);
    }


    @PutMapping("/user/manageUser/{id}")
    public ResponseEntity<String> UserBlockUnblock( @PathVariable("id") Integer id ,@RequestParam Integer btnValue){
        return adminService.UserBlockUnblock(id, btnValue);
    }





}
