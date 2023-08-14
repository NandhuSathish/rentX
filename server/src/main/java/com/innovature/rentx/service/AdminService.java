package com.innovature.rentx.service;

import com.innovature.rentx.exception.BadRequestException;
import com.innovature.rentx.form.AdminForm;
import com.innovature.rentx.form.ChangePasswordForm;
import com.innovature.rentx.form.EmailForm;
import com.innovature.rentx.form.LoginForm;
import com.innovature.rentx.form.OtpForm;
import com.innovature.rentx.form.RefreshTokenForm;
import com.innovature.rentx.util.Pager;
import com.innovature.rentx.view.AdminLoginView;
import com.innovature.rentx.view.AdminVendorView;
import com.innovature.rentx.view.EmailTokenView;
import com.innovature.rentx.view.RefreshTokenView;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

public interface AdminService {


    RefreshTokenView refresh(RefreshTokenForm form) throws BadRequestException;


    ResponseEntity<String>add(AdminForm form);

    AdminLoginView login(LoginForm form) throws BadRequestException;
    
    EmailTokenView forgetPasswordEmail(EmailForm form)throws BadRequestException;

    EmailTokenView forgetVerify(@Valid OtpForm form);

    ResponseEntity<String> changePassword(@Valid ChangePasswordForm form);


    ResponseEntity<String> approveReject(Integer id,Integer btnValue);

    Pager<AdminVendorView>listVendor(String searchData,Integer page, Integer limit,String sort,String order,Integer statusValue);

    Pager<AdminVendorView>listUser(String searchData,Integer page, Integer limit,String sort,String order);

    ResponseEntity<String>UserBlockUnblock(Integer id,Integer btnValue);







}
