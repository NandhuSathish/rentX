package com.innovature.rentx.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AdminTest {

    @Test
    public void testAdminConstructor() {
        String email = "admin@example.com";
        String password = "password123";

        Admin admin = new Admin(email, password);

        Assertions.assertEquals(email, admin.getEmail());
        Assertions.assertEquals(password, admin.getPassword());
        Assertions.assertEquals(Admin.Role.ADMIN.value, admin.getRole());
        Assertions.assertEquals(Admin.Status.INACTIVE.value, admin.getStatus());

    }

    @Test
    public void testAdminId() {
        Admin admin = new Admin();
        Integer id = 1;

        admin.setId(id);

        Assertions.assertEquals(id, admin.getId());
    }


}
