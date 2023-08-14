package com.innovature.rentx.controller;

import com.innovature.rentx.form.LoginForm;
import com.innovature.rentx.form.RefreshTokenForm;
import com.innovature.rentx.service.UserService;
import com.innovature.rentx.view.LoginView;
import com.innovature.rentx.view.RefreshTokenView;
import com.innovature.rentx.view.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping
    public LoginView login(@Valid @RequestBody LoginForm form){
        return userService.login(form);
    }

    @PutMapping
    public RefreshTokenView refresh(@RequestBody RefreshTokenForm form){
        return userService.refresh(form);
    }


    @GetMapping("/currentUser")
    public UserView currentUser() {
        return userService.currentUser();
    }
    


}
