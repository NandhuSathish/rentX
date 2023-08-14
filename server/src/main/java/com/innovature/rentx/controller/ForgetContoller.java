package com.innovature.rentx.controller;

import com.innovature.rentx.form.ChangePasswordForm;
import com.innovature.rentx.form.EmailForm;
import com.innovature.rentx.form.OtpForm;
import com.innovature.rentx.service.UserService;
import com.innovature.rentx.view.EmailTokenView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping
public class ForgetContoller {

    @Autowired
    private UserService userService;

    @PostMapping("forgetPassword")
    public EmailTokenView forgetemail(@Valid @RequestBody EmailForm form){
        return userService.forgetPasswordEmail(form);
    }

    @PostMapping("/otp/verify/forgetpassword")
    public EmailTokenView forgetverify(@Valid @RequestBody OtpForm form) {

        return userService.forgetVerify(form);
    }
    @PutMapping("/changePassword")
    public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePasswordForm form){
        return userService.changePassword(form);
    }




}
