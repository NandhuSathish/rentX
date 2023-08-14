package com.innovature.rentx.controller;

import com.innovature.rentx.service.UserService;
import com.innovature.rentx.view.EmailTokenView;
import com.innovature.rentx.form.OtpForm;
import com.innovature.rentx.form.ResendOtpForm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.ValidationException;

public class OtpControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private OtpController otpController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testVerifyEndpoint_Success() {
        // Arrange
        OtpForm otpForm = new OtpForm();
        Mockito.when(userService.verify(otpForm)).thenReturn(null);
    
        // Act
        ResponseEntity<String> response = otpController.verify(otpForm);
    
        // Assert
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Mockito.verify(userService, Mockito.times(1)).verify(otpForm);
    }
    
    @Test
    void testVerifyEndpoint_ValidationFailure() {
        // Arrange
        OtpForm otpForm = new OtpForm();
        Mockito.doThrow(ValidationException.class).when(userService).verify(otpForm);

        // Act and Assert
        Assertions.assertThrows(ValidationException.class, () -> {
            otpController.verify(otpForm);
        });

        Mockito.verify(userService, Mockito.times(1)).verify(otpForm);
    }

    // @Test
    // void testResendEndpoint_Success() {
    //     // Arrange
    //     ResendOtpForm resendOtpForm = new ResendOtpForm();
    //     EmailTokenView emailTokenView = new EmailTokenView(null, null);
    //     Mockito.when(userService.resend(resendOtpForm)).thenReturn(emailTokenView);

    //     // Act
    //     EmailTokenView response = otpController.resend(resendOtpForm);

    //     // Assert
    //     Assertions.assertEquals(emailTokenView, response);
    //     Mockito.verify(userService, Mockito.times(1)).resend(resendOtpForm);
    // }
}
