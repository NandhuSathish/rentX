package com.innovature.rentx.controller;

import com.innovature.rentx.form.LoginForm;
import com.innovature.rentx.form.RefreshTokenForm;
import com.innovature.rentx.service.UserService;
import com.innovature.rentx.view.LoginView;
import com.innovature.rentx.view.RefreshTokenView;
import com.innovature.rentx.view.UserView;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @InjectMocks
    LoginController loginController;
    @Mock
    private UserService userService;

    @Mock
    private LoginView loginView;
    @Mock
    private RefreshTokenView refreshTokenView;


    private MockMvc mockMvc;

    @Before
    public void setup(){

        LoginForm form=new LoginForm();
        form.setEmail("admin@example.com");
        form.setPassword("Admin@123");
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

@Test
public void LoginAPI()throws Exception {
        LoginForm form =new LoginForm();
        form.setEmail("admin@example.com");
        form.setPassword("Admin@123");
    Mockito.when(loginController.login(form)).thenReturn(loginView);
    Mockito.when(userService.login(form)).thenReturn(loginView);
    String url="http://localhost:8080/login";
    mockMvc.perform(post(url))
            .andExpect(status().isBadRequest());


}

@Test
public void RefreshAPI() throws Exception{
        RefreshTokenForm form =new RefreshTokenForm();
        form.setRefreshToken("b9f1d21007796713fa51af463aa7ff60aca0cba823dfa6d133c7a1ff10f6f591845a562a46d18afdc30f016a6af1b11107c4f17ab7ef45a7f0e80e4147187788dd99a77d8dee67a35c8a515190660bbc30607f1cb369be3b317908bce70e68f0646fa7eb6cf422a48db5a07afde3b44d2f980a653386a9ebeec175218ce3e40bfdbcea8aa9602b52aef89baf6c883dd49f26c200709ac0f6cdef2ea814144d4243b321e3ffbbb974b945f611e6f053d3");
        Mockito.when(loginController.refresh(form)).thenReturn(refreshTokenView);

}

    @Test
    public void currentUserAPI() throws Exception {
        String url = "/login/currentUser";
        UserView userView = new UserView();
        Mockito.when(loginController.currentUser()).thenReturn(userView);
        mockMvc.perform(get(url))
                .andExpect(status().isOk());
    }

    @Test
    public void currentUserAPINull() throws Exception {
        String url = "/login/currentUser";
        Mockito.when(loginController.currentUser()).thenReturn(null);
        mockMvc.perform(get(url))
                .andExpect(status().isOk());

    }





}