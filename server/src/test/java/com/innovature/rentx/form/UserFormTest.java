package com.innovature.rentx.form;

import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;

public class UserFormTest {
 
    @Test
    public void UserFormTest1(){

        UserForm userForm= new UserForm();
        
        userForm.setEmail("negil@gmail.com");
        userForm.setPassword("Neg@1234");

        assertEquals("negil@gmail.com", userForm.getEmail());
    }
}
