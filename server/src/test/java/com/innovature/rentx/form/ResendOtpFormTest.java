package com.innovature.rentx.form;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class ResendOtpFormTest {

    @Test
    public void testResendOtpForm() {
        // Create a ResendOtpForm
        ResendOtpForm form = new ResendOtpForm();
        form.setEmailToken("1234567890");

        // Validate the ResendOtpForm
        Assertions.assertTrue(form.isValid());

        // Assert that the ResendOtpForm is valid
        Assertions.assertEquals("1234567890", form.getEmailToken());
    }

    @Test
    public void testResendOtpFormWithInvalidEmailToken() {
        // Create a ResendOtpForm with an invalid email token
        ResendOtpForm form = new ResendOtpForm();
        form.setEmailToken("123456789");

        // Validate the ResendOtpForm
        Assertions.assertFalse(form.isValid());
    }

    @Test
    public void testResendOtpFormWithEmptyEmailToken() {
        // Create a ResendOtpForm with an empty email token
        ResendOtpForm form = new ResendOtpForm();
        form.setEmailToken("");

        Assertions.assertFalse(form.isValid());
    }
}
