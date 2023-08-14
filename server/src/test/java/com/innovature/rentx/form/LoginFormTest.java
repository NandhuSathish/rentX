package com.innovature.rentx.form;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoginFormTest {

    @Test
    public void LoginForm() {
        LoginForm loginForm = new LoginForm();

        loginForm.setEmail("admin@example.com");
        loginForm.setPassword("password123");

        assertEquals("admin@example.com", loginForm.getEmail());
        assertEquals("password123", loginForm.getPassword());
    }

}