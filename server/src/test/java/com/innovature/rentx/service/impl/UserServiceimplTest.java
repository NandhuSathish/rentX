package com.innovature.rentx.service.impl;

import com.innovature.rentx.entity.User;
import com.innovature.rentx.form.LoginForm;
import com.innovature.rentx.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.innovature.rentx.RentxApplication;
import com.innovature.rentx.entity.Otp;
import com.innovature.rentx.form.UserForm;
import com.innovature.rentx.repository.OtpRepository;
import com.innovature.rentx.security.config.SecurityConfig;
import com.innovature.rentx.security.util.TokenGenerator;
import com.innovature.rentx.util.EmailUtil;
import com.innovature.rentx.util.LanguageUtil;
import com.innovature.rentx.view.LoginView;

import org.junit.jupiter.api.Assertions;

@ContextConfiguration
@SpringBootTest(classes = RentxApplication.class)
@ExtendWith(SpringExtension.class)
public class UserServiceimplTest {

    @Mock
    private static UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userServiceimpl;

    @Mock
    private LoginForm loginForm;

    @Mock
    private OtpRepository otpRepository;

    @Mock
    private TokenGenerator tokenGenerator;

    @Mock
    private SecurityConfig securityConfig;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private LanguageUtil languageUtil;

    @Mock
    private EmailUtil emailUtil;

    @Mock
    private PasswordEncoder passwordEncoder;


    @Test
    public void testLogin(){

        
        User user = new User("john.doe@example.com", "password");
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));
        LoginView loginView1=userService.login(loginForm);
        assertNotNull(loginView1);


    }

    @Test
    public void login() {

        MockitoAnnotations.openMocks(this);
        User user = new User();
        user.setId(1);
        user.setEmail("Aswin@gmail.com");
        user.setPassword("Aswin@123");
        user.setStatus((byte) 0);
        user.setRole((byte) 0);
        user.setType((byte) 0);
        when(userRepository.findByEmailId(user.getEmail())).thenReturn(user);
        when(user.getPassword() == user.getPassword()).thenReturn(true);
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

       
        

    }


    @Test
    void testAdds() {
        UserForm userForm = new UserForm();
        userForm.setEmail("sample@gmail.com");
        userForm.setPassword("Sample@123");

        User user = new User(1);
        user.setEmail("sample@gmail.com");
        user.setPassword("Sample@123");
        user.setId(1);

        doReturn(user).when(userRepository).save(ArgumentMatchers.any());

        // String emailToken = userService.add(userForm).getEmailToken();
        // assertEquals(emailToken, userService.add(userForm).getEmailToken());

    }

    @Test
    public void testOtpAdd() {
        Otp otpEntity = new Otp();
        otpEntity.setOtp("123456");
        otpEntity.setEmail("sample@gmail.com");

        Assertions.assertEquals(123456, otpEntity.getOtp());
        assertEquals("sample@gmail.com", otpEntity.getEmail());

    }

    @Test
    public void testUserAdd() {
        UserForm userForm = new UserForm();
        userForm.setEmail("sample@gmail.com");
        userForm.setPassword("Sample@123");

        assertEquals("sample@gmail.com", userForm.getEmail());
        assertEquals("Sample@123", userForm.getPassword());

    }

}
