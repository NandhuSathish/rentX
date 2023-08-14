package com.innovature.rentx.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innovature.rentx.form.GoogleSignInForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class VendorServiceImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    public void testGoogleSignIn() throws Exception {
        // Arrange
        GoogleSignInForm signInForm = new GoogleSignInForm();
        signInForm.setToken("your_token_here");
    
        // Act
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/google/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signInForm)))
                .andReturn();
    
        // Assert
        assertThat(mvcResult.getResponse().getStatus()).isEqualTo(403);
    }
    

}

