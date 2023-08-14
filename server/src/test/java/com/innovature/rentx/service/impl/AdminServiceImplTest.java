package com.innovature.rentx.service.impl;

import com.innovature.rentx.entity.User;
import com.innovature.rentx.exception.BadRequestException;
import com.innovature.rentx.repository.UserRepository;
import com.innovature.rentx.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class AdminServiceImplTest {


    @Mock
    private UserRepository userRepository;

    @Mock
    private AdminService adminService;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testApproveReject() {
        // Create a user with a status of STAGE2
        User user = new User();
        user.setId(1);
        user.setRole(User.Role.VENDOR.value);
        user.setStatus(User.Status.STAGE2.value);
        userRepository.save(user);

        // Test approving the user
        ResponseEntity<String> response = adminService.approveReject(1, 0);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(User.Status.ACTIVE.value, userRepository.findById(1).get().getStatus());

        // Test rejecting the user
        response = adminService.approveReject(1, 1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(User.Status.INACTIVE.value, userRepository.findById(1).get().getStatus());

        // Test deleting the user
        response = adminService.approveReject(1, 2);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(User.Status.DELETE.value, userRepository.findById(1).get().getStatus());

        // Test providing an invalid button value
        try {
            adminService.approveReject(1, 3);
            fail("Expected BadRequestException not thrown");
        } catch (BadRequestException e) {
            assertEquals("Unable to perform the requested action", e.getMessage());
        }

        // Test providing an invalid user ID
        try {
            adminService.approveReject(2, 0);
            fail("Expected BadRequestException not thrown");
        } catch (BadRequestException e) {
            assertEquals("Invalid ID provided", e.getMessage());
        }
    }

}