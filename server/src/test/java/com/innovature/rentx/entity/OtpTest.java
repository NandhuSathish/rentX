package com.innovature.rentx.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class OtpTest {

    @Test
    void testGetterAndSetter() {

        Otp otp = new Otp();

        otp.setEmail("negilbabu@gmail.com");
        otp.setOtp("123456");

        assertEquals("negilbabu@gmail.com", otp.getEmail());
        assertEquals(123456, otp.getOtp());

    }

}
