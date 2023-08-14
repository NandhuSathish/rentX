package com.innovature.rentx.controller;

import com.innovature.rentx.controller.VendorController;
import com.innovature.rentx.entity.User;
import com.innovature.rentx.exception.BadRequestException;
import com.innovature.rentx.form.*;
import com.innovature.rentx.service.ProductService;
import com.innovature.rentx.service.StoreService;
import com.innovature.rentx.service.UserService;
import com.innovature.rentx.service.VendorService;
import com.innovature.rentx.service.impl.UserServiceImpl;
import com.innovature.rentx.service.impl.UserServiceimplTest;
import com.innovature.rentx.util.LanguageUtil;
import com.innovature.rentx.util.Pager;
import com.innovature.rentx.view.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class VendorControllerTest {

    @Mock
    private User user;

    @Mock
    private VendorOtpView  vendorOtpView;

    @Mock
    private EmailTokenView emailTokenView;


    @Mock
    private UserService userService;
    
    @Mock
    private UserServiceImpl userServiceImpl;

    @InjectMocks
    private VendorController vendorController;

    // @Mock
    // private 

    private MockMvc mockMvc;


    @BeforeEach
    public void setUp() {
        OtpForm form = new OtpForm();
        form.setEmailToken("emailTokenExample");
        form.setOtp("123456");
     
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(vendorController).build();

    }

    @Test
public void testVerifyVendorOtp() {
    // Create a new VendorController instance.
    VendorController vendorController = new VendorController();

    // Set the userService property to the UserService instance that is injected into the test class.
    vendorController.userService = userService;

    // Create a new OtpForm instance.
    OtpForm otpForm = new OtpForm();
    otpForm.setOtp("123456");
    otpForm.setEmailToken("emailTokenExample");

    // Call the verifyVendorOtp() method.
    VendorOtpView vendorOtpView = vendorController.verifyVendorOtp(otpForm);

    // Assert that the verifyVendorOtp() method returned a non-null value.
    // This means that the NullPointerException has been resolved.
    assertNotNull(vendorOtpView);
}
    // @Test
    // public void testVerifyVendorOtp() {
    //     // Create a new VendorController instance.
    //     VendorController vendorController = new VendorController();

    //     // Set the userService property to a non-null value.
    //     vendorController.userService = new UserService();

    //     // Create a new OtpForm instance.
    //     OtpForm otpForm = new OtpForm();
    //     otpForm.setOtp("123456");

    //     // Call the verifyVendorOtp() method.
    //     vendorController.verifyVendorOtp(otpForm);

    //     // Assert that the verifyVendorOtp() method did not throw an exception.
    //     // This means that the NullPointerException has been resolved.
    // }
    // @Test
    // public void testVerifyVendorOtp() throws Exception {
    //     // Arrange
    //     OtpForm form = new OtpForm();
    //     form.setEmailToken("emailTokenExample");
    //     form.setOtp("123456");
    //     String url = "/vendor/otp/verify";

    //     Mockito.when(vendorController.verifyVendorOtp(form)).thenReturn(vendorOtpView);
    //     // mockMvc.perform(post(url))
    //     mockMvc.perform(post(url))
    //     .andExpect(status().isBadRequest());   
    
    // }


    @Test
public void testConstructorAndGetters() {
    String message = "Test Message";
    String emailToken = "Test Token";

    EmailTokenView emailTokenView = new EmailTokenView(message, emailToken);

    Assertions.assertEquals(message, emailTokenView.getMessage());
    Assertions.assertEquals(emailToken, emailTokenView.getEmailToken());
}

@Test
public void testSetToken() {
    String token = "Test Token";
    EmailTokenView emailTokenView = new EmailTokenView("", "");

    emailTokenView.setToken(token);
}


    @Test
    public void testGoogleAuth() throws BadRequestException, GeneralSecurityException, IOException {
        GoogleSignInForm form = new GoogleSignInForm();
        UserView expectedUserView = new UserView();
        Mockito.when(userService.googleAuth(form, User.Role.VENDOR.value)).thenReturn(expectedUserView);

        UserView result = vendorController.googleAuth(form);

        Assertions.assertEquals(expectedUserView, result);
    }


    @Test
    public void testAdd() {
        UserForm form = new UserForm();
        form.setEmail("test@example.com");
        form.setPassword("Password@123");
        String msg = "success";
        String emailToken = "newEmailToken";

        EmailTokenView expectedEmailTokenView = new EmailTokenView(msg, emailToken);
        when(userService.add(form, User.Role.VENDOR.value)).thenReturn(expectedEmailTokenView);



        EmailTokenView result = vendorController.add(form);

        Assertions.assertEquals(expectedEmailTokenView,result);
    }
   

}
