package com.innovature.rentx.controller;

import com.innovature.rentx.entity.User;
import com.innovature.rentx.exception.BadRequestException;
import com.innovature.rentx.form.GoogleSignInForm;
import com.innovature.rentx.form.UserForm;
import com.innovature.rentx.service.UserService;
import com.innovature.rentx.view.EmailTokenView;
import com.innovature.rentx.view.UserView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.validation.ValidationException;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddEndpoint_Success() {
        // Arrange
        UserForm userForm = new UserForm();
        EmailTokenView emailTokenView = new EmailTokenView(null, null);
        Mockito.when(userService.add(userForm, User.Role.USER.value)).thenReturn(emailTokenView);

        // Act
        EmailTokenView response = userController.add(userForm);

        // Assert
        Assertions.assertEquals(emailTokenView, response);
        Mockito.verify(userService, Mockito.times(1)).add(userForm, User.Role.USER.value);
    }

    @Test
    void testAddEndpoint_ValidationFailure() {
        // Arrange
        UserForm userForm = new UserForm();
        Mockito.doThrow(ValidationException.class).when(userService).add(userForm, User.Role.USER.value);

        // Act and Assert
        Assertions.assertThrows(ValidationException.class, () -> {
            userController.add(userForm);
        });

        Mockito.verify(userService, Mockito.times(1)).add(userForm, User.Role.USER.value);
    }

    @Test
    void testGoogleAuthEndpoint_Success() throws BadRequestException, GeneralSecurityException, IOException {
        // Arrange
        GoogleSignInForm googleSignInForm = new GoogleSignInForm();
        UserView userView = new UserView();
        Mockito.when(userService.googleAuth(googleSignInForm, User.Role.USER.value)).thenReturn(userView);

        // Act
        UserView response = userController.googleAuth(googleSignInForm);

        // Assert
        Assertions.assertEquals(userView, response);
        Mockito.verify(userService, Mockito.times(1)).googleAuth(googleSignInForm, User.Role.USER.value);
    }

    @Test
    void testGoogleAuthEndpoint_Exception() throws BadRequestException, GeneralSecurityException, IOException {
        // Arrange
        GoogleSignInForm googleSignInForm = new GoogleSignInForm();
        Mockito.doThrow(BadRequestException.class).when(userService).googleAuth(googleSignInForm, User.Role.USER.value);

        // Act and Assert
        Assertions.assertThrows(BadRequestException.class, () -> {
            userController.googleAuth(googleSignInForm);
        });

        Mockito.verify(userService, Mockito.times(1)).googleAuth(googleSignInForm, User.Role.USER.value);
    }
}