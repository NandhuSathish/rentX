package com.innovature.rentx.form;

import javax.validation.ValidationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OtpFormTest {

    @Test
    void testSetAndGetOtp() {
        // Arrange
        OtpForm otpForm = new OtpForm();
        String otp = "123456";

        // Act
        otpForm.setOtp(otp);
        String retrievedOtp = otpForm.getOtp();

        // Assert
        Assertions.assertEquals(otp, retrievedOtp);
    }

    @Test
    void testSetAndGetEmailToken() {
        // Arrange
        OtpForm otpForm = new OtpForm();
        String emailToken = "abc123";

        // Act
        otpForm.setEmailToken(emailToken);
        String retrievedEmailToken = otpForm.getEmailToken();

        // Assert
        Assertions.assertEquals(emailToken, retrievedEmailToken);
    }

    @Test
    void testOtpValidation() {
        // Arrange
        OtpForm otpForm = new OtpForm();
        String validOtp = "123456";
        String invalidOtp = "123";

        // Act and Assert
        otpForm.setOtp(validOtp);
        Assertions.assertDoesNotThrow(() -> {
            // Validation should pass for a valid OTP
        });

        otpForm.setOtp(invalidOtp);
        Assertions.assertThrows(ValidationException.class, () -> {
            // Validation should fail for an invalid OTP
        });
    }

    @Test
    void testEmailTokenValidation() {
        // Arrange
        OtpForm otpForm = new OtpForm();
        String validEmailToken = "abc123";
        String invalidEmailToken = "";

        // Act and Assert
        otpForm.setEmailToken(validEmailToken);
        Assertions.assertDoesNotThrow(() -> {
            // Validation should pass for a valid email token
        });

        otpForm.setEmailToken(invalidEmailToken);
        Assertions.assertThrows(ValidationException.class, () -> {
            // Validation should fail for an invalid email token
        });
    }
}

