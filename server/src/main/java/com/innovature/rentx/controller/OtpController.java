package com.innovature.rentx.controller;

import com.innovature.rentx.service.UserService;
import com.innovature.rentx.view.EmailTokenView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.innovature.rentx.form.OtpForm;
import com.innovature.rentx.form.ResendOtpForm;

@RestController
@RequestMapping("/otp")
public class OtpController {

    @Autowired
    private UserService userService;

    @PostMapping("verify")
    public ResponseEntity<String> verify(@Valid @RequestBody OtpForm form) {

        userService.verify(form);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/resend/{forgetValue}")
    public EmailTokenView resend(@Valid @RequestBody ResendOtpForm form, @PathVariable Integer forgetValue) {
        return userService.resend(form, forgetValue);
    }

}
