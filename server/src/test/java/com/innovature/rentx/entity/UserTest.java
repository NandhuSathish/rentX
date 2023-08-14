package com.innovature.rentx.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {

    User user = new User();

    
    @Test
    void testGetterandSetter() {

       
        user.setEmail("admin@example.com");
        user.setPassword("Admin@123");
        user.setPhone("9446162875");
        user.setUsername("admin");
        user.setRole((byte) 0);
        user.setStatus((byte) 0);



        Assertions.assertEquals("admin@example.com", user.getEmail());
        Assertions.assertEquals("Admin@123", user.getPassword());
        Assertions.assertEquals("9446162875", user.getPhone());
        Assertions.assertEquals("admin", user.getUsername());
        Assertions.assertEquals(0, user.getRole());
        Assertions.assertEquals(0, user.getStatus());

    }

    
}
